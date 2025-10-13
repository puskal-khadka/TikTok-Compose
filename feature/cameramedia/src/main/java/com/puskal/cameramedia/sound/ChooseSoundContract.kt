package com.puskal.cameramedia.sound

import com.puskal.data.model.AudioModel

data class ViewState(
    val audioFiles: List<AudioModel>? = null
)

sealed class SoundEvent
