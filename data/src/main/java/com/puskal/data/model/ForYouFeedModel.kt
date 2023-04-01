package com.puskal.data.model

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
data class ForYouFeedModel(
    val user: User,
    val videoStats: VideoStats,
    val music: Audio,
    val video: Video,
) {
    data class User(
        val id: Long,
        val userName: String,
        val fullName: String,
        val profilePic: String,
    )

    data class VideoStats(
        val like: Int,
        val comment: Int,
        val share: Int
    )

    data class Video(
        val url: String,
        val videoCaption: String,
        val hasTag: List<HasTag>
    ) {
        data class HasTag(
            val hasTagId: Long,
            val title: String
        )
    }

    data class Audio(
        val url: String
    )
}