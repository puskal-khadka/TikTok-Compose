# Camera Video Recording Flow

This document explains what happens under the hood when the camera screen records a video and saves it to local storage.

## Technical Stack

- **Jetpack Compose** – UI toolkit used for building the camera screen (`CameraScreen.kt`).
- **CameraView** – Open‑source camera library consumed via the Maven artifact `com.otaliastudios:cameraview`. It uses the underlying `Camera1`/`Camera2` APIs and `MediaCodec` to encode media.
- **CameraX** – Included only for the sample `CameraGlPreviewView` class. The main camera preview relies on `CameraView`.
- **mp4compose** – Library available for post‑processing and video editing with OpenGL filters.
- **Kotlin + Coroutines** – Main language and concurrency toolkit.

## Process Overview

1. **Permissions**
   - The screen requests `CAMERA` and `RECORD_AUDIO` permissions. If not granted, the UI prompts the user.
2. **Camera Setup**
   - `CameraScreen` creates an `AndroidView` wrapper hosting `CameraView`.
   - `CameraView` is bound to the current `LifecycleOwner` and configured with the desired facing mode.
  - The selected filter from the `Filters` enum is applied directly via `cameraView.filter`. `CameraGlPreviewView` exists as a demonstration of using `mp4compose` but is not part of the normal flow.
3. **Recording Start**
   - When the user selects the *Video* capture option, `CameraView` is put in `Mode.VIDEO` and `takeVideo(File)` is invoked.
   - The file passed to `takeVideo` is created inside `context.filesDir` using a timestamped name (`video_<timestamp>.mp4`).
4. **Data Flow During Recording**
   - `CameraView` routes frames from the camera to a `CameraEngine` implementation, which uses `MediaCodec` to encode video (and audio when enabled).
   - Encoded frames are written to the provided file via a muxer (`MuxRender`), resulting in a standard MP4 file.
   - While recording, the app keeps the screen awake and updates UI state (`isRecording`).
5. **Recording Stop**
   - Stopping occurs when the user taps the capture button again or when any configured duration/size limits are reached.
   - `CameraView.stopVideo()` finalizes the encoder and triggers `onVideoTaken(VideoResult)`.
   - The `VideoResult` exposes `getFile()` which points to the same file created earlier.
6. **Local Storage**
   - Because the path uses `context.filesDir`, the video resides in the app’s private storage directory (`/data/data/<package>/files`).
   - The app has full read/write access to this location, but other apps cannot directly access it unless the file is moved or shared.

## Key Code Snippet

The main logic for recording a video is located in `CameraScreen.kt`:

```kotlin
cameraView.mode = Mode.VIDEO
if (isRecording) {
    cameraView.stopVideo()
} else {
    val file = File(context.filesDir, "video_${'$'}{System.currentTimeMillis()}.mp4")
    cameraView.takeVideo(file)
    isRecording = true
}
```
【F:feature/cameramedia/src/main/java/com/puskal/cameramedia/tabs/CameraScreen.kt†L290-L297】

This snippet creates the output file and passes it to `takeVideo`. When recording finishes, `onVideoTaken` is invoked to reset UI state.

## Performance Notes

- Recording uses hardware‑accelerated codecs through `MediaCodec`, resulting in efficient encoding with minimal CPU overhead.
- Saving to internal storage avoids permission issues associated with external storage. Write throughput depends on device I/O performance; for short clips this is typically not a bottleneck.
- `CameraView` keeps a dedicated thread for encoding, reducing frame drops. However, filters or GL effects may increase GPU load.

## Summary

The camera screen leverages `CameraView` to capture video. It writes the recording to an app‑private MP4 file under `context.filesDir`. The process integrates CameraX/GL for preview and optional filtering, while `MediaCodec` handles encoding, ensuring smooth performance for short social‑style clips.
