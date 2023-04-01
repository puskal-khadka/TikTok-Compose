package com.puskal.creatorprofile.screen.creatorvideo

import com.puskal.data.model.VideoModel

/**
 * Created by Puskal Khadka on 3/22/2023.
 */
data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val creatorVideosList: List<VideoModel>? = null
)

sealed class CreatorVideoEvent {
}

