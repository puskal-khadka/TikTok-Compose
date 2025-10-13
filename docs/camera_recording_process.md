# Camera Screen Video Recording Process

This document describes the technical stack and the end-to-end flow that occurs when the camera screen is used to record a video in the TikTok-Compose project.

## Technical Stack

- **CameraView Library** – The `CameraMedia` feature relies on the third‑party library [CameraView](https://github.com/natario1/CameraView) to control the camera. The library supports both the legacy `CAMERA1` and the newer `CAMERA2` engine. The recommended preview implementation is the OpenGL `GL_SURFACE` which provides real‑time filters and snapshot support.
- **Jetpack Compose** – All UI components for the camera screen are implemented using Jetpack Compose. The `CameraView` is embedded in Compose using `AndroidView`.
- **mp4compose** – Library included for post‑processing and editing. Real‑time preview uses the filters bundled with `CameraView`.

## Process Overview

1. **Permission Handling**
   - On opening the camera screen, the app requests `CAMERA` and `RECORD_AUDIO` permissions if not already granted.
   - If permission is denied, the `CameraMicrophoneAccessPage` prompts the user to grant access via the system dialog or settings.
2. **Camera Setup**
   - A `CameraView` instance is created and bound to the lifecycle using `setLifecycleOwner`.
   - Default settings include `Mode.PICTURE` and `Facing.FRONT`, but the user can switch between front and back cameras.
   - Filters selected by the user are applied by setting `cameraView.filter` to an instance from `Filters`.
3. **Capturing Media**
   - When the capture button is pressed, the screen switches the mode to `Mode.VIDEO` and calls `takeVideo(file)` to start recording. The resulting file is stored in the app’s private directory.
   - Recording status is tracked with `isRecording` and toggled with `stopVideo()`.
   - Picture capture is also supported using `takePicture()` when in `PICTURE` mode.
   - Camera callbacks such as `onVideoTaken` are registered via a `CameraListener` to know when recording completes.
4. **Preview and UI**
   - The preview is rendered full screen using `AndroidView`. Compose overlays provide capture controls and filter selection.
   - The preview size automatically adapts based on the view bounds. `MATCH_PARENT` can crop the preview while `WRAP_CONTENT` shows the entire frame.
5. **Post Processing**
   - Filters from mp4compose can be used for post‑processing video if desired. Example filter list resides in `feature/cameramedia/src/main/java/com/puskal/cameramedia/edit/VideoFilter.kt`.

## Performance Considerations

- The `GL_SURFACE` preview is recommended because it enables video snapshots and provides efficient capture of picture snapshots without additional rotation or EXIF handling. This is noted in the CameraView documentation where `Preview.GL_SURFACE` is described as the preferred option for real‑time filters and overlays.
- Snapshot video capture requires API 18 or higher and uses the OpenGL preview path. Standard `takeVideo` captures at the configured video size and bit rate.
- Frame processing or heavy filters may impact recording performance. The app currently toggles recording state and writes output to a file on the device, keeping CPU/GPU usage minimal during simple recordings.

## References

- [CameraView capturing docs](https://natario1.github.io/CameraView/docs/capturing-media/)
- [Camera engine and preview options](https://natario1.github.io/CameraView/docs/previews/)
