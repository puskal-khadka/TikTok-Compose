package com.puskal.data.source

import android.content.Context
import com.puskal.data.model.AudioModel
import com.puskal.data.source.UsersDataSource.userList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object AudioDataSource {

    fun fetchAudios(context: Context): Flow<List<AudioModel>> = flow {
        val assets = context.assets
        val audioFiles = assets.list("audios")?.filter { it.endsWith(".mp3") } ?: emptyList()
        val covers = assets.list("audios/cover")?.filter { it.endsWith(".jpg") } ?: emptyList()

        val rnd = Random(System.currentTimeMillis())
        val result = audioFiles.mapIndexed { index, file ->
            val cover = covers.getOrElse(index) { covers.random(rnd) }
            val author = userList[index % userList.size]
            val duration = String.format("%02d:%02d", rnd.nextInt(1, 4), rnd.nextInt(0, 60))
            val posts = rnd.nextLong(1000, 300000)

            AudioModel(
                audioCoverImage = cover,
                isOriginal = true,
                audioAuthor = author,
                numberOfPost = posts,
                duration = duration,
                originalVideoUrl = file
            )
        }
        emit(result)
    }
}
