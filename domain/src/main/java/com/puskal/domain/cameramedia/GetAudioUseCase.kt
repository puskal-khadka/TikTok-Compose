package com.puskal.domain.cameramedia

import com.puskal.data.model.AudioModel
import com.puskal.data.repository.camermedia.AudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAudioUseCase @Inject constructor(
    private val audioRepository: AudioRepository
) {
    operator fun invoke(): Flow<List<AudioModel>> {
        return audioRepository.getAudios()
    }
}
