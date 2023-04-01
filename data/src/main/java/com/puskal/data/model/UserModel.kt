package com.puskal.data.model

import com.puskal.core.extension.formattedCount

/**
 * Created by Puskal Khadka on 3/18/2023.
 */
data class UserModel(
    val userId: Long,
    val uniqueUserName: String,
    val fullName: String,
    val following: Long,
    val followers: Long,
    val likes: Long,
    val bio: String,
    val profilePic: String,
    val isVerified: Boolean,
    val isLikedVideoPrivate: Boolean = true,
    val pinSocialMedia: SocialMedia? = null
) {
    var formattedFollowingCount: String = ""
    var formattedFollowersCount: String = ""
    var formattedLikeCount: String = ""

    init {
        formattedLikeCount = likes.formattedCount()
        formattedFollowingCount = following.formattedCount()
        formattedFollowersCount = followers.formattedCount()
    }

    data class SocialMedia(
        val type: SocialMediaType,
        val link: String
    )
}

enum class SocialMediaType {
    INSTAGRAM,
    YOUTUBE
}