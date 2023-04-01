package com.puskal.data.model

/**
 * Created by Puskal Khadka on 3/18/2023.
 */
data class AudioModel(
    val audioCoverImage:String,
    val isOriginal:Boolean,
    val audioAuthor:UserModel,
    val numberOfPost:Long,
    val originalVideoUrl:String,
)