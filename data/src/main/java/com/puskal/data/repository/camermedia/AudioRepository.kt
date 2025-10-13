package com.puskal.data.repository.camermedia

import android.content.Context
import com.puskal.data.model.AudioModel
import com.puskal.data.source.AudioDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

class AudioRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getAudios(): Flow<List<AudioModel>> {
        return AudioDataSource.fetchAudios(context)
    }
}
