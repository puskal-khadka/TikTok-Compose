package com.puskal.creatorprofile.screen.creatorprofile.tabs

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.puskal.creatorprofile.component.VideoGrid
import com.puskal.creatorprofile.screen.creatorprofile.CreatorProfileViewModel
import com.puskal.data.model.VideoModel

/**
 * Created by Puskal Khadka on 3/25/2023.
 */

@Composable
fun PublicVideoTab(
    viewModel: CreatorProfileViewModel,
    scrollState: ScrollState,
    onClickVideo: (video: VideoModel, index: Int) -> Unit
) {
    val creatorPublicVideos by viewModel.publicVideosList.collectAsState()
    VideoGrid(
        scrollState = scrollState,
        videoList = creatorPublicVideos,
        onClickVideo = onClickVideo
    )
}