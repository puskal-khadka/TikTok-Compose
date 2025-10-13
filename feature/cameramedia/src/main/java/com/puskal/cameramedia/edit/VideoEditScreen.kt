package com.puskal.cameramedia.edit

import android.net.Uri
import android.util.Log                       // ← ADD
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.puskal.cameramedia.MusicBarLayout
import com.puskal.composable.RoundedButton
import com.puskal.theme.R
import com.puskal.theme.TikTokTheme
import com.puskal.theme.White
import com.puskal.theme.GrayMainColor

private const val TAG = "VideoEditScreen"


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoEditScreen(
    videoUri: String,
    onClickBack: () -> Unit,
    onTrimVideo: (String) -> Unit = {},
    onClickAddSound: () -> Unit = {},
    enableFilters: Boolean = false,
    onClickNext: (String) -> Unit = {}
) {
    TikTokTheme(darkTheme = true) {
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RoundedButton(
                        buttonText = stringResource(id = R.string.friend_daily),
                        modifier = Modifier.weight(1f),
                        containerColor = GrayMainColor
                    ) {}
                    Spacer(modifier = Modifier.width(12.dp))
                    RoundedButton(
                        buttonText = stringResource(id = R.string.next),
                        modifier = Modifier.weight(1f)
                    ) { onClickNext(videoUri) }
                }
            }
        ) { padding ->
            val context = LocalContext.current

            /*************** STATE *****************/
            var showResizeMenu   by remember { mutableStateOf(false) }
            var showFilterSheet  by remember(enableFilters) { mutableStateOf(false) }
            var selectedFilter   by remember(enableFilters) { mutableStateOf(VideoFilter.NONE) }
            var isPlayerPrepared by remember { mutableStateOf(false) }

            /*************** PLAYER *****************/
            val exoPlayer = remember(videoUri) {
                Log.d(TAG, "Create ExoPlayer for $videoUri")
                ExoPlayer.Builder(context).build().apply {
                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            val stateName = when (state) {
                                Player.STATE_IDLE    -> "IDLE"
                                Player.STATE_BUFFERING -> "BUFFERING"
                                Player.STATE_READY   -> "READY"
                                Player.STATE_ENDED   -> "ENDED"
                                else -> "UNKNOWN"
                            }
                            Log.d(TAG, "Player state = $stateName")
                        }

                        override fun onRenderedFirstFrame() {
                            Log.d(TAG, "Rendered first video frame")
                        }

                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            Log.d(TAG, "isPlaying = $isPlaying")
                        }
                    })
                }
            }
            DisposableEffect(exoPlayer) {
                onDispose {
                    Log.d(TAG, "Release ExoPlayer")
                    exoPlayer.release()
                }
            }

            /*************** VIEW *****************/
            val playerView = if (enableFilters) {
                remember {
                    Log.d(TAG, "Create GlFilterPlayerView")
                    GlFilterPlayerView(context)
                }
            } else {
                remember {
                    Log.d(TAG, "Create PlayerView")
                    PlayerView(context).apply {
                        useController = false
                        resizeMode    = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        layoutParams  = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                }
            }

            DisposableEffect(playerView) {
                onDispose {
                    Log.d(TAG, "Dispose playerView")
                    when (playerView) {
                        is GlFilterPlayerView -> playerView.player = null
                        is PlayerView         -> playerView.player = null
                    }
                }
            }

            /* Attach player once (before surface update) */
            LaunchedEffect(exoPlayer, playerView) {
                Log.d(TAG, "Attach player to view")
                when (playerView) {
                    is GlFilterPlayerView -> playerView.player = exoPlayer
                    is PlayerView         -> playerView.player = exoPlayer
                }
            }

            /* Update GL filter at runtime */
            if (enableFilters) {
                LaunchedEffect(selectedFilter) {
                    Log.d(TAG, "Apply filter: $selectedFilter")
                    (playerView as GlFilterPlayerView).setGlFilter(selectedFilter.create())
                }
            }

            /*************** UI *****************/
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                /*** Video Surface / Texture ***/
                AndroidView(
                    factory = { playerView },
                    modifier = Modifier.fillMaxSize(),
                    update = { view ->
                        when (view) {
                            is GlFilterPlayerView -> {
                                view.player = exoPlayer
                                Log.d(TAG, "GLView available=${view.isAvailable}")
                                if (!isPlayerPrepared && view.isAvailable) {
                                    prepareAndPlay(exoPlayer, videoUri)
                                    isPlayerPrepared = true
                                }
                            }
                            is PlayerView -> {
                                view.player = exoPlayer
                                if (!isPlayerPrepared) {
                                    prepareAndPlay(exoPlayer, videoUri)
                                    isPlayerPrepared = true
                                }
                            }
                        }
                    }
                )

                /*** Back Icon ***/
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 32.dp)
                        .size(24.dp)
                        .clickable {
                            Log.d(TAG, "Back pressed")
                            onClickBack()
                        }
                )

                /*** Music bar ***/
                MusicBarLayout(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 32.dp),
                    onClickAddSound = {
                        Log.d(TAG, "Add sound clicked")
                        onClickAddSound()
                    }
                )

                /*** Resize toolbar ***/
                if (showResizeMenu) {
                    ResizeToolBar(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 64.dp)
                    )
                }

                /*** Main toolbar ***/
                VideoEditToolBar(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp),
                    showFilters = enableFilters,
                    onToolSelected = { tool ->
                        Log.d(TAG, "Tool selected: $tool")
                        when (tool) {
                            VideoEditTool.CROP_RESIZE -> showResizeMenu = !showResizeMenu
                            VideoEditTool.TRIM        -> onTrimVideo(videoUri)
                            VideoEditTool.FILTERS     -> if (enableFilters) showFilterSheet = true
                            else                      -> {}
                        }
                    },
                    onFeatureSelected = { feature ->
                        Log.d(TAG, "Feature selected: $feature")
                        when (feature) {
                            ResizeMenuFeature.COLLAPSE_TOOLBAR -> {}
                            else -> {}
                        }
                    }
                )

                /*** Filter sheet ***/
                if (enableFilters && showFilterSheet) {
                    VideoFilterBottomSheet(
                        currentFilter = selectedFilter,
                        onSelectFilter = {
                            Log.d(TAG, "Filter chosen: $it")
                            selectedFilter  = it
                            showFilterSheet = false
                        },
                        onDismiss = {
                            Log.d(TAG, "Dismiss filter sheet")
                            showFilterSheet = false
                        }
                    )
                }
            }
        }
    }
}

/* -------------- helpers --------------- */
private fun prepareAndPlay(player: ExoPlayer, uri: String) {
    Log.d(TAG, "prepareAndPlay -> $uri")
    player.setMediaItem(MediaItem.fromUri(Uri.parse(uri)))
    player.repeatMode    = Player.REPEAT_MODE_ONE
    player.prepare()
    player.playWhenReady = true
}
