package com.redevrx.video_trimmer.utils


//class VideoOptions(private var ctx: Context) {
//    companion object {
//        const val TAG = "VideoOptions"
//    }
//
//    fun trimAndCropVideo(
//        width: Int,
//        height: Int,
//        x: Int,
//        y: Int,
//        bitrate: Double,
//        totalVideoDuration: Long,
//        startPosition: String,
//        endPosition: String,
//        inputPath: String,
//        outputPath: String,
//        outputFileUri: Uri,
//        listener: OnVideoEditedEvent?
//    ) {
//        val command = StringBuilder()
//            .append("-y") //overWrite
//            .append(" -i ").append(inputPath)
//            .append(" -ss ").append(startPosition)
//            .append(" -to ").append(endPosition)
//            .append(" -filter:v ")
//            .append(" crop=").append("$width:$height:$x:$y")
//            .append(" -vcodec libx264 -b ${bitrate}M -preset ultrafast ")
//            .append(" -c:a copy ").append(outputPath)
////
////        FFmpegKitConfig.enableStatisticsCallback { newStatistics ->
////            val timeInMilliseconds = newStatistics.time;
////            if (timeInMilliseconds > 0) {
////                val completePercentage = (timeInMilliseconds * 100) / totalVideoDuration
////                listener?.onProgress(completePercentage.toInt())
////            }
////        }
////
////        val session = FFmpegKit.execute(command.toString())
////        if (ReturnCode.isSuccess(session.returnCode)) {
////            // SUCCESS2
////            listener?.getResult(outputFileUri)
////            Log.e(TAG, "onFinish: ")
////        } else if (ReturnCode.isCancel(session.returnCode)) {
////            // CANCEL
////            listener?.onError("CANCEL")
////            Log.e(TAG, "isCancel: " + "CANCEL")
////        } else {
////            // FAILURE
////            listener?.onError("Failed")
////            Log.e(TAG, "onFailure: " + "Failed")
////        }
//
//    }
//}