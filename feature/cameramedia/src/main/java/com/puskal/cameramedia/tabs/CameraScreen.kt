package com.puskal.cameramedia.tabs

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Engine
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Mode
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import java.io.File
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import android.content.Context
import android.content.ContentValues
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.FileOutputStream
import com.puskal.cameramedia.*
import com.puskal.cameramedia.filter.FilterBottomSheet
import com.puskal.composable.CaptureButton
import com.puskal.core.extension.MediumSpace
import com.puskal.core.extension.Space
import com.puskal.core.utils.openAppSetting
import com.puskal.core.DestinationRoute
import com.puskal.theme.LightGreenColor
import com.puskal.theme.R
import com.puskal.theme.TealColor
import com.puskal.theme.White
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import com.otaliastudios.cameraview.filter.Filters
import com.otaliastudios.cameraview.filter.Filter as CameraFilter
import com.daasuu.mp4compose.composer.Mp4Composer

/**
 * Created by Puskal Khadka on 4/2/2023.
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraMediaViewModel,
    cameraOpenType: Tabs = Tabs.CAMERA
) {
    val context = LocalContext.current
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO
        )
    )
    LaunchedEffect(key1 = Unit) {
        if (!multiplePermissionState.permissions[0].status.isGranted) {
            multiplePermissionState.launchMultiplePermissionRequest()
        }
    }

    val fileLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia()
        ) { uris ->
            uris.firstOrNull()?.let { uri ->
                navController.navigate("${DestinationRoute.VIDEO_EDIT_ROUTE}/${Uri.encode(uri.toString())}")
            }
        }

    Column(modifier = Modifier.fillMaxSize()) {
        if (multiplePermissionState.permissions[0].status.isGranted) {
            CameraPreview(
                navController = navController,
                cameraOpenType = cameraOpenType,
                onClickCancel = { navController.navigateUp() },
                onClickOpenFile = {
                    fileLauncher.launch(pickVisualMediaRequest)
                },
                onClickAddSound = {
                    navController.navigate(DestinationRoute.CHOOSE_SOUND_ROUTE)
                }
            )
        } else {
            CameraMicrophoneAccessPage(multiplePermissionState.permissions[1].status.isGranted,
                cameraOpenType,
                onClickCancel = { navController.navigateUp() },
                onClickOpenFile = { fileLauncher.launch(pickVisualMediaRequest) }) {
                val permissionState = when (it) {
                    PermissionType.CAMERA -> multiplePermissionState.permissions[1]
                    PermissionType.MICROPHONE -> multiplePermissionState.permissions[1]
                }
                permissionState.apply {
                    if (this.status.shouldShowRationale) {
                        this.launchPermissionRequest()
                    } else {
                        context.openAppSetting()
                    }
                }
            }
        }
    }
}

@Composable
fun CameraMicrophoneAccessPage(
    isMicrophonePermissionGranted: Boolean,
    cameraOpenType: Tabs,
    onClickCancel: () -> Unit,
    onClickOpenFile: () -> Unit,
    onClickGrantPermission: (PermissionType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        TealColor, LightGreenColor
                    )
                )
            )
            .padding(top = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_cancel),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
                .align(Alignment.Start)
                .clickable { onClickCancel() })
        MediumSpace()

        Text(
            text = stringResource(id = R.string.allow_tiktok_to_access_your),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.75f)
        )
        MediumSpace()

        Text(
            text = "${stringResource(id = R.string.take_photos_record_videos_or_preview)} ${
                stringResource(id = R.string.you_can_change_preference_in_setting)
            }",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.75f),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
        30.dp.Space()

        Card(
            modifier = Modifier.fillMaxWidth(0.75f), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically)
            ) {
                Row(
                    modifier = Modifier.clickable {
                        onClickGrantPermission(PermissionType.CAMERA)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.access_camera),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (!isMicrophonePermissionGranted) {
                    Divider(color = White.copy(alpha = 0.2f))
                    Row(
                        modifier = Modifier.clickable {
                            onClickGrantPermission(PermissionType.MICROPHONE)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_microphone),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(id = R.string.access_microhpone),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        FooterCameraController(
            cameraOpenType = cameraOpenType,
            onClickEffect = { },
            onClickOpenFile = onClickOpenFile,
            onOptionChanged = {},
            onClickCapture = { },
            isEnabledLayout = false
        )
    }
}

@Composable
fun CameraPreview(
    navController: NavController,
    cameraOpenType: Tabs,
    onClickCancel: () -> Unit,
    onClickOpenFile: () -> Unit,
    onClickAddSound: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var defaultCameraFacing by remember { mutableStateOf(Facing.FRONT) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(Filters.NONE) }
    var isMirror by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val cameraView = remember {
        CameraView(context).apply {
            setExperimental(true)
            setEngine(Engine.CAMERA2)
            setRequestPermissions(false)
            mode = Mode.PICTURE
            facing = defaultCameraFacing
            mapGesture(Gesture.PINCH, GestureAction.ZOOM)
            mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)
            mapGesture(Gesture.LONG_TAP, GestureAction.TAKE_PICTURE)
        }
    }

    DisposableEffect(lifecycleOwner) {
        cameraView.setLifecycleOwner(lifecycleOwner)
        onDispose { cameraView.setLifecycleOwner(null) }
    }
    LaunchedEffect(selectedFilter) {
        cameraView.filter = selectedFilter.newInstance()
    }
    LaunchedEffect(defaultCameraFacing) {
        cameraView.facing = defaultCameraFacing
    }
    LaunchedEffect(isMirror) {
        cameraView.scaleX = if (isMirror) -1f else 1f
    }
    var isRecording by remember { mutableStateOf(false) }
    var recordedFile by remember { mutableStateOf<File?>(null) }

    fun handleVideoFile(file: File) {
        val galleryUri = saveVideoToGallery(context, file)
        isRecording = false
        val uriString = (galleryUri ?: Uri.fromFile(file)).toString()
        navController.navigate(
            "${DestinationRoute.VIDEO_EDIT_ROUTE}/${Uri.encode(uriString)}"
        )
    }

    DisposableEffect(cameraView) {
        val listener = object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                val file = File(context.filesDir, "image_${'$'}{System.currentTimeMillis()}.jpg")
                result.toBitmap { bitmap ->
                    val processed = if (isMirror) bitmap?.mirrorHorizontally() else bitmap
                    processed?.let { bmp ->
                        coroutineScope.launch(Dispatchers.IO) {
                            FileOutputStream(file).use { out ->
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
                            }
                        }
                    }
                }
            }

            override fun onVideoTaken(result: VideoResult) {
                val srcFile = recordedFile ?: result.getFile()
                if (isMirror) {
                    val mirrorFile = File(context.filesDir, "mirror_${'$'}{srcFile.name}")
                    Mp4Composer(srcFile.absolutePath, mirrorFile.absolutePath)
                        .flipHorizontal(true)
                        .listener(object : Mp4Composer.Listener {
                            override fun onProgress(progress: Double) {}
                            override fun onCurrentWrittenVideoTime(timeUs: Long) {}
                            override fun onCanceled() {}
                            override fun onFailed(exception: Exception) {
                                coroutineScope.launch { handleVideoFile(srcFile) }
                            }

                            override fun onCompleted() {
                                coroutineScope.launch { handleVideoFile(mirrorFile) }
                                srcFile.delete()
                            }
                        })
                        .start()
                } else {
                    coroutineScope.launch { handleVideoFile(srcFile) }
                }
                recordedFile = null
            }
        }
        cameraView.addCameraListener(listener)
        onDispose { cameraView.removeCameraListener(listener) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { cameraView },
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { scaleX = if (isMirror) -1f else 1f }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            FooterCameraController(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                cameraOpenType = cameraOpenType,
                onClickEffect = { },
                onClickOpenFile = onClickOpenFile,
                onOptionChanged = { option ->
                    when (option) {
                        CameraCaptureOptions.PHOTO -> cameraView.mode = Mode.PICTURE
                        CameraCaptureOptions.VIDEO,
                        CameraCaptureOptions.FIFTEEN_SECOND,
                        CameraCaptureOptions.SIXTY_SECOND -> cameraView.mode = Mode.VIDEO
                        else -> {}
                    }
                },
                onClickCapture = { option ->
                    when (option) {
                        CameraCaptureOptions.PHOTO -> {
                            cameraView.takePicture()
                        }
                        CameraCaptureOptions.VIDEO,
                        CameraCaptureOptions.FIFTEEN_SECOND,
                        CameraCaptureOptions.SIXTY_SECOND -> {
                            if (isRecording) {
                                cameraView.stopVideo()
                            } else {
                                val file = File(
                                    context.filesDir,
                                    "video_${'$'}{System.currentTimeMillis()}.mp4"
                                )
                                recordedFile = file
                                cameraView.takeVideo(file)
                                isRecording = true
                            }
                        }
                        else -> {}
                    }
                },
                isEnabledLayout = true
            )

            CameraSideControllerSection(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp),
                defaultCameraFacing
            ) {
                when (it) {
                    CameraController.FLIP -> {
                        defaultCameraFacing = if (defaultCameraFacing == Facing.FRONT) Facing.BACK else Facing.FRONT
                    }
                    CameraController.FILTERS -> {
                        showFilterSheet = true
                    }
                    else -> {}
                }
            }

            Icon(painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .clickable {
                        onClickCancel()
                    })

            MusicBarLayout(
                modifier = Modifier.align(Alignment.TopCenter),
                onClickAddSound = onClickAddSound
            )
            if (showFilterSheet) {
                FilterBottomSheet(
                    currentFilter = selectedFilter,
                    onSelectFilter = {
                        selectedFilter = it
                        showFilterSheet = false
                    },
                    onDismiss = { showFilterSheet = false }
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalSnapperApi::class)
@Composable
fun FooterCameraController(
    modifier: Modifier = Modifier.fillMaxWidth(),
    cameraOpenType: Tabs,
    onClickEffect: () -> Unit,
    onClickOpenFile: () -> Unit,
    onOptionChanged: (CameraCaptureOptions) -> Unit = {},
    onClickCapture: (CameraCaptureOptions) -> Unit,
    isEnabledLayout: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val backgroundShapeWidth = 62.dp
    val itemHorizontalPadding = 26.dp
    val screenHalfWidth = LocalConfiguration.current.screenWidthDp.div(2).dp
    val horizontalContentPadding = screenHalfWidth.minus(itemHorizontalPadding.div(2))
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = when (cameraOpenType) {
            Tabs.CAMERA -> 0
            Tabs.STORY -> 1
            else -> 0
        }
    )

    val layoutInfo = rememberLazyListSnapperLayoutInfo(
        lazyListState = lazyListState,
        endContentPadding = screenHalfWidth.minus(30.dp),
        snapOffsetForItem = SnapOffsets.Center
    )
    val captureOptions = remember {
        when (cameraOpenType) {
            Tabs.CAMERA -> listOf(
                CameraCaptureOptions.VIDEO,
                CameraCaptureOptions.PHOTO
            )
            Tabs.STORY -> CameraCaptureOptions.values().toMutableList().apply {
                removeAll(
                    listOf(CameraCaptureOptions.FIFTEEN_SECOND, CameraCaptureOptions.SIXTY_SECOND)
                )
            }
            else -> emptyList()
        }
    }

    val selectedIndex = layoutInfo.currentItem?.index ?: 0
    LaunchedEffect(selectedIndex) {
        captureOptions.getOrNull(selectedIndex)?.let { onOptionChanged(it) }
    }

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .alpha(alphaForInteractiveView(isEnabledLayout))
                    .size(width = backgroundShapeWidth, height = 22.dp)
                    .background(color = White, shape = RoundedCornerShape(16.dp))
            )
            LazyRow(
                modifier = Modifier.alpha(alphaForInteractiveView(isEnabledLayout)),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                state = lazyListState,
                userScrollEnabled = isEnabledLayout,
                contentPadding = PaddingValues(horizontal = horizontalContentPadding),
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
            ) {
                itemsIndexed(captureOptions) { index, it ->
                    val isCurrentItem = layoutInfo.currentItem?.index == index
                    Text(
                        text = it.value,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.clickable {
                            if (isEnabledLayout) {
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(index)
                                }
                            }
                        },
                        color = if (isCurrentItem) Color.Black else Color.White
                    )
                }
            }
        }

        MediumSpace()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable {
                    if (isEnabledLayout) {
                        onClickEffect()
                    }
                }) {
                Image(
                    painter = painterResource(id = R.drawable.img_effect_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(alphaForInteractiveView(isEnabledLayout))
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = stringResource(id = R.string.effects),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.alpha(alphaForInteractiveView(isEnabledLayout))
                )
            }
            val captureButtonColor =
                when (captureOptions.getOrNull(layoutInfo.currentItem?.index ?: -1)) {
                    CameraCaptureOptions.FIFTEEN_SECOND, CameraCaptureOptions.SIXTY_SECOND, CameraCaptureOptions.VIDEO -> MaterialTheme.colorScheme.primary
                    else -> White
                }
            CaptureButton(
                modifier = Modifier.alpha(alphaForInteractiveView(isEnabledLayout)),
                color = captureButtonColor,
                onClickCapture = {
                    captureOptions.getOrNull(layoutInfo.currentItem?.index ?: -1)?.let { option ->
                        onClickCapture(option)
                    }
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable { onClickOpenFile() }) {
                Image(
                    painter = painterResource(id = R.drawable.img_upload_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = stringResource(id = R.string.upload),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        MediumSpace()
    }
}

@Composable
fun CameraSideControllerSection(
    modifier: Modifier,
    defaultCameraFacing: Facing,
    onClickController: (CameraController) -> Unit
) {
    val controllers = CameraController.values().toMutableList().apply {
        if (defaultCameraFacing == Facing.FRONT) remove(CameraController.FLASH)
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        controllers.forEach { controller ->
            val enabled = when (controller) {
                CameraController.FLASH -> defaultCameraFacing == Facing.BACK
                else -> true
            }
            ControllerItem(controller, enabled, onClickController)
        }
    }
}

@Composable
fun ControllerItem(
    cameraController: CameraController,
    enabled: Boolean = true,
    onClickController: (CameraController) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .alpha(if (enabled) 1f else 0.4f)
            .clickable(enabled = enabled) {
                onClickController(cameraController)
            }
    ) {
        Icon(
            imageVector = cameraController.icon(),
            contentDescription = null,
            modifier = Modifier.size(27.dp),
            tint = Color.White.copy(alpha = 0.8f)
        )
        Text(
            text = stringResource(id = cameraController.title),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

val pickVisualMediaRequest by lazy {
    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
}

fun alphaForInteractiveView(isEnabledLayout: Boolean): Float = if (isEnabledLayout) 1f else 0.28f

private fun saveVideoToGallery(context: Context, file: File): Uri? {
    val values = ContentValues().apply {
        put(MediaStore.Video.Media.DISPLAY_NAME, file.name)
        put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES)
    }
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
    uri?.let { outputUri ->
        resolver.openOutputStream(outputUri)?.use { output ->
            file.inputStream().use { input ->
                input.copyTo(output)
            }
        }
        MediaScannerConnection.scanFile(context, arrayOf(outputUri.toString()), null, null)
    }
    return uri
}

private fun Bitmap.mirrorHorizontally(): Bitmap {
    val matrix = Matrix().apply { preScale(-1f, 1f) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}


