package com.puskal.core.utils

import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build


/**
 * Created by Puskal Khadka on 3/16/2023.
 */
object FileUtils {

    fun extractThumbnail(assetFileDesc: AssetFileDescriptor?, atTime: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val retriever: MediaMetadataRetriever
        if (assetFileDesc != null && assetFileDesc.fileDescriptor.valid()) try {
            retriever = MediaMetadataRetriever()
            assetFileDesc.apply {
                retriever.setDataSource(
                    fileDescriptor,
                    startOffset,
                    length
                )
            }

            bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                retriever.getScaledFrameAtTime(
                    atTime.toLong(),
                    MediaMetadataRetriever.OPTION_CLOSEST,
                    1280,
                    720
                )
            } else {
                retriever.getFrameAtTime(atTime.toLong())
            }
            assetFileDesc.close()
            retriever.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}