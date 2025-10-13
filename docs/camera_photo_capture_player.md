# Camera Photo Capture Flow

This document explains what happens when the user captures a photo from the camera screen in the app and how the player component fits in.

## Technical Stack

- **Jetpack Compose** and **Material 3** for UI
- **Hilt** for dependency injection
- **Coroutines** for asynchronous tasks
- **Media3 / ExoPlayer** for video playback
- **CameraX** with the `CameraView` dependency (`com.otaliastudios:cameraview`) for capturing photos and videos
- Modular architecture following Clean Architecture and MVVM

The full list of main libraries is highlighted in the project README.

## Capture Process Overview

1. **Permissions Check** – The `CameraScreen` composable requests `CAMERA` and `RECORD_AUDIO` permissions. If not granted, a permission screen is shown.
2. **Camera Preview** – When permissions are granted, a `CameraView` instance is created and attached to the lifecycle. The preview supports switching front/back cameras and applying filters.
3. **Photo Capture** – Pressing the capture button while the `Photo` option is active triggers `cameraView.takePicture()`.
4. **Result Handling** – `CameraListener.onPictureTaken()` receives the `PictureResult`. The image is written to a file under the app’s internal storage.
5. **Video Capture / Playback** – If the user records a video, `cameraView.takeVideo(file)` saves the footage. Videos are played back using the `VideoPlayer` composable which wraps an `ExoPlayer` instance.

## Relevant Code Snippets

**Camera preview and capture**
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
...
FooterCameraController(
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
)
```
This logic resides in `CameraScreen.kt` and shows how a photo is taken or a video is started/stopped.

**Video player**
```kotlin
val exoPlayer = remember(context) {
    ExoPlayer.Builder(context).build().apply {
        videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        repeatMode = Player.REPEAT_MODE_ONE
        setMediaItem(MediaItem.fromUri(Uri.parse("asset:///videos/${video.videoLink}")))
        playWhenReady = true
        prepare()
        addListener(object : Player.Listener {
            override fun onRenderedFirstFrame() {
                thumbnail = thumbnail.copy(second = false)
            }
        })
    }
}
```
The `VideoPlayer` composable (used in the feed and after capture) configures an `ExoPlayer` instance for playback.

**GL Preview**
```kotlin
fun surfaceProvider(executor: Executor): Preview.SurfaceProvider = Preview.SurfaceProvider { request ->
    val texture = surfaceTextureWrapper?.surfaceTexture
    if (texture != null) {
        request.provideSurface(Surface(texture), executor) { }
    }
}
```
`CameraGlPreviewView` demonstrates how CameraX frames could be fed through `mp4compose` filters. The main camera screen instead applies filters via the built-in `CameraView` pipeline.

## Performance Notes

- Photo capture and saving run on the main thread via the `CameraView` library. For heavy work (like generating thumbnails) `Dispatchers.IO` is used.
- ExoPlayer handles video playback efficiently with frame callbacks to hide the placeholder thumbnail when the first frame renders.
- The OpenGL preview draws frames on a dedicated EGL context to keep UI rendering smooth.

## Summary

When the camera screen is opened, a `CameraView` provides a real-time preview. Selecting the *Photo* capture option triggers `takePicture()`, producing a file in internal storage via `onPictureTaken()`. If the user records a video, `takeVideo()` is invoked and the resulting file can be played through the `VideoPlayer` component built on Media3’s `ExoPlayer`. Filters are applied via `cameraView.filter` using the `Filters` enum; `CameraGlPreviewView` is only a sample for custom OpenGL processing.
