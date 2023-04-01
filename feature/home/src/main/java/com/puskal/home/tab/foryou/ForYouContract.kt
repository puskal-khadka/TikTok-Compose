package com.puskal.home.tab.foryou

import com.puskal.data.model.VideoModel

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
data class ViewState(
    val forYouPageFeed: List<VideoModel>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null,
)

sealed class ForYouEvent {
}