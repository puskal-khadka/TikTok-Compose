# Camera Recording Flow

This document explains the internal workflow when recording a video from the camera screen in the TikTok‑Compose project.

## Technology Stack

The project is built with modern Android technologies. The overall tech stack is highlighted in the project `README.md`:

```
- Jetpack Compose
- Material 3
- Coroutine
- Accompanist
- Hilt
- Coil
- Multi Module
- Kotlin DSL
- Media 3
- CameraX and so on
```

Source: `README.md` lines 18–30.

For the camera feature, the `feature:cameramedia` module depends on the CameraX libraries plus Maven artifacts for `CameraView` and `mp4compose`:

```
dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
    cameraXDependencies()
    media3Dependency()
    implementation(project(":feature:video-trimmer"))
    implementation("com.otaliastudios:cameraview:2.7.2")
    implementation("com.github.MasayukiSuda:Mp4Composer-android:v0.4.1")
}
```

Source: `feature/cameramedia/build.gradle.kts` lines 1–15.

`CameraView` itself is an advanced wrapper over Camera2 that provides simplified APIs for video capture.

## Process Flow

### Opening the Camera

1. `CameraMediaScreen` sets up the screen. On entry, it adjusts the window brightness so that the preview is easier to see, restoring the previous brightness on dispose:

```kotlin
val minimumScreenBrightness = 0.25f
DisposableEffect(key1 = Unit) {
    val attrs = (context as Activity).window.attributes.apply {
        if (context.getCurrentBrightness() < minimumScreenBrightness) {
            screenBrightness = minimumScreenBrightness
        }
    }
    context.window.attributes = attrs
    onDispose {
        context.window.attributes = attrs.apply {
            screenBrightness = context.getCurrentBrightness()
        }
    }
}
```

Source: `CameraMediaScreen.kt` lines 35–58.

2. Camera and microphone permissions are handled via `rememberMultiplePermissionsState`. If not granted, the app requests them or shows guidance to the user:

```kotlin
val multiplePermissionState = rememberMultiplePermissionsState(
    permissions = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.RECORD_AUDIO
    )
)
LaunchedEffect(key1 = Unit) {
    if (!multiplePermissionState.permissions[0].status.isGranted) {
        multiplePermissionState.launchMultiplePermissionRequest()
    }
}
```

Source: `CameraScreen.kt` lines 71–83.

### Preview Setup

When permissions are granted, `CameraPreview` is shown. A `CameraView` instance is created and bound to the lifecycle:

```kotlin
val cameraView = remember {
    CameraView(context).apply {
        setLifecycleOwner(lifecycleOwner)
        mode = Mode.PICTURE
        facing = defaultCameraFacing
        mapGesture(Gesture.PINCH, GestureAction.ZOOM)
        mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)
        mapGesture(Gesture.LONG_TAP, GestureAction.TAKE_PICTURE)
    }
}
```

Source: `CameraScreen.kt` lines 244–251.

Filters selected by the user are applied by updating `cameraView.filter` and the facing camera can be flipped dynamically.

A `CameraListener` is registered to react to picture/video results:

```kotlin
val listener = object : CameraListener() {
    override fun onPictureTaken(result: PictureResult) {
        val file = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")
        result.toFile(file) {}
    }

    override fun onVideoTaken(result: VideoResult) {
        isRecording = false
    }
}
cameraView.addCameraListener(listener)
```

Source: `CameraScreen.kt` lines 254–266.

### Capturing a Video

The floating controls are implemented in `FooterCameraController`. When the capture button is pressed with the `VIDEO` option, recording starts or stops depending on `isRecording`:

```kotlin
onClickCapture = { option ->
    when (option) {
        CameraCaptureOptions.PHOTO -> {
            cameraView.mode = Mode.PICTURE
            cameraView.takePicture()
        }
        CameraCaptureOptions.VIDEO -> {
            cameraView.mode = Mode.VIDEO
            if (isRecording) {
                cameraView.stopVideo()
            } else {
                val file = File(context.filesDir, "video_${System.currentTimeMillis()}.mp4")
                cameraView.takeVideo(file)
                isRecording = true
            }
        }
        else -> {}
    }
}
```

Source: `CameraScreen.kt` lines 284–301.

This stores the captured video in the app’s private files directory. When `onVideoTaken` is triggered, `isRecording` is set back to `false`.

### Filtered Preview

Real‑time effects on the camera screen rely on the filter support built in to
`CameraView`.  When a user selects a value from the `Filters` enumeration the
composable updates `cameraView.filter = selectedFilter.newInstance()` so both the
preview and the recorded video use that effect.


The repository also contains a `CameraGlPreviewView` class that demonstrates how
`mp4compose` `GlFilter` implementations could be applied to CameraX frames. This
view is provided as a sample only and is not wired into the current Compose UI:

```kotlin
class CameraGlPreviewView(context: Context) : TextureView(context), TextureView.SurfaceTextureListener {
    private var surfaceTextureWrapper: GlSurfaceTexture? = null
    private var previewFilter: GlPreviewFilter? = null
    private var glFilter: GlFilter = GlFilter()
    ...
    fun surfaceProvider(executor: Executor): Preview.SurfaceProvider = Preview.SurfaceProvider { request ->
        val texture = surfaceTextureWrapper?.surfaceTexture
        if (texture != null) {
            request.provideSurface(Surface(texture), executor) { }
        }
    }
    ...
    private fun drawFrame() {
        val wrapper = surfaceTextureWrapper ?: return
        wrapper.updateTexImage()
        wrapper.getTransformMatrix(stMatrix)
        GLES20.glViewport(0, 0, width, height)
        android.opengl.Matrix.setIdentityM(mvpMatrix, 0)
        previewFilter?.draw(texName, mvpMatrix, stMatrix, 1f)
        glFilter.draw(texName, null)
        EGL14.eglSwapBuffers(eglDisplay, eglSurface)
    }
}
```

Source: `CameraGlPreviewView.kt` lines 1–141.

This class manages its own EGL context for rendering and can be used for custom real‑time effects, though it is not part of the default camera flow.

## Performance Notes

- `CameraView` operates on top of Camera2 and handles recording through optimized components (`Full2VideoRecorder`, `AudioMediaEncoder`, etc.) provided by the upstream library. The artifact ships with performance tweaks like frame processing size limits.
- The custom OpenGL preview performs rendering on a texture surface using shaders, which is efficient but should be handled on a separate thread if extended. The current implementation runs in the view’s update callback and swaps buffers for each frame.
- Screen brightness is temporarily increased to 25% or more during recording to improve visibility without affecting the user’s global setting.

## Summary

When the user navigates to the camera screen:
1. Permissions are requested as needed.
2. A `CameraView` instance is created and attached to the lifecycle.
3. The preview is shown along with UI controls for capture, switching camera, and applying filters.
4. Tapping the capture button in video mode calls `cameraView.takeVideo()` and stores the recording to a file. When finished, `onVideoTaken` is triggered and recording stops.
5. Selected filters are applied through `CameraView`'s `Filters` enum. `CameraGlPreviewView` is available as an example of applying `mp4compose` filters but is not used in the current UI.

This flow leverages Jetpack Compose for UI and `CameraView` for preview and recording.  `mp4compose` is included primarily for video editing and custom effects rather than the live camera preview.
