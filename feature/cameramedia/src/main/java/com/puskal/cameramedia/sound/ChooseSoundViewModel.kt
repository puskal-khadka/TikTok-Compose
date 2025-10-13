package com.puskal.cameramedia.sound

import androidx.lifecycle.viewModelScope
import com.puskal.core.base.BaseViewModel
import com.puskal.domain.cameramedia.GetAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseSoundViewModel @Inject constructor(
    private val getAudioUseCase: GetAudioUseCase
) : BaseViewModel<ViewState, SoundEvent>() {

    init {
        fetchAudios()
    }

    private fun fetchAudios() {
        viewModelScope.launch {
            getAudioUseCase().collect {
                updateState(ViewState(audioFiles = it))
            }
        }
    }

    override fun onTriggerEvent(event: SoundEvent) {}
}
