package com.puskal.data.model

import android.net.Uri

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
data class ContentCreatorFollowingModel(
    val userModel: UserModel,
    val coverVideo: VideoModel
) {
    fun parseVideo(): Uri = Uri.parse("asset:///videos/${coverVideo.videoLink}")
}