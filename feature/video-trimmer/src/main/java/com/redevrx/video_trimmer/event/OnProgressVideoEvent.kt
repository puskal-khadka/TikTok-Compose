package com.redevrx.video_trimmer.event

interface OnProgressVideoEvent {
    fun updateProgress(time: Float, max: Long, scale: Long)
}