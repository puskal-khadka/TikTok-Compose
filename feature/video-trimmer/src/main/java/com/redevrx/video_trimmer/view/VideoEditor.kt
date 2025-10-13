package com.redevrx.video_trimmer.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.transformer.Composition
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.EditedMediaItemSequence
import androidx.media3.transformer.Effects
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.TransformationRequest
import androidx.media3.ui.AspectRatioFrameLayout
import com.redevrx.video_trimmer.R
import com.redevrx.video_trimmer.databinding.TrimmerViewLayoutBinding
import com.redevrx.video_trimmer.event.OnProgressVideoEvent
import com.redevrx.video_trimmer.event.OnRangeSeekBarEvent
import com.redevrx.video_trimmer.event.OnVideoEditedEvent
import com.redevrx.video_trimmer.utils.BackgroundExecutor
import com.redevrx.video_trimmer.utils.TrimVideoUtils
import com.redevrx.video_trimmer.utils.UiThreadExecutor
import java.io.File
import java.lang.ref.WeakReference
import java.util.Locale
import java.util.UUID
import javax.xml.transform.Transformer

class VideoEditor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mPlayer: ExoPlayer
    private lateinit var mSrc: Uri
    private var mFinalPath: String? = null

    private var mMaxDuration: Int = -1
    private var mMinDuration: Int = -1
    private var mListeners: ArrayList<OnProgressVideoEvent> = ArrayList()

    private var mOnVideoEditedListener: OnVideoEditedEvent? = null
//    private var mOnVideoListener: OnVideoEvent? = null

    private lateinit var binding: TrimmerViewLayoutBinding

    private var mDuration:Long = 0L
    private var mTimeVideo = 0L
    private var mStartPosition = 0L

    private var mEndPosition = 0L
    private var mResetSeekBar = false
    private val mMessageHandler = MessageHandler(this)
    private var originalVideoWidth: Int = 0
    private var originalVideoHeight: Int = 0
    private var videoPlayerWidth: Int = 0
    private var videoPlayerHeight: Int = 0
    private var bitRate: Int = 2
    private var isVideoPrepared = false
    private var videoPlayerCurrentPosition = 0L
    private var destinationPath: String
        get() {
            if (mFinalPath == null) {
                val folder = context.cacheDir
                mFinalPath = folder.path + File.separator
            }
            return mFinalPath ?: ""
        }
        set(finalPath) {
            mFinalPath = finalPath
        }

    init {
        init(context)
    }

    private fun init(context: Context) {
        binding = TrimmerViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        setUpListeners()
        setUpMargins()
    }

    /**
     * Register an [OnProgressVideoEvent] to receive progress updates of the currently
     * playing video. The listener will be called whenever the player position
     * changes.
     */
    fun addOnProgressVideoEvent(listener: OnProgressVideoEvent) {
        mListeners.add(listener)
    }

    /** Current playback position of the player in milliseconds */
    val currentPosition: Long
        get() = if (this::mPlayer.isInitialized) mPlayer.currentPosition else 0L

    /** Total duration of the loaded video in milliseconds */
    val totalDuration: Long
        get() = mDuration

    /** Whether the player is currently playing */
    val isPlaying: Boolean
        get() = if (this::mPlayer.isInitialized) mPlayer.isPlaying else false

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpListeners() {
        mListeners = ArrayList()
        mListeners.add(object : OnProgressVideoEvent {
            override fun updateProgress(time: Float, max: Long, scale: Long) {
                updateVideoProgress(time.toLong())
            }
        })

        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    onClickVideoPlayPause()
                    return true
                }
            })

        binding.iconVideoPlay.setOnClickListener {
            onClickVideoPlayPause()
        }

        binding.layoutSurfaceView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        binding.handlerTop.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                onPlayerIndicatorSeekChanged(progress, fromUser)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                onPlayerIndicatorSeekStart()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onPlayerIndicatorSeekStop(seekBar)
            }
        })

        binding.timeLineBar.addOnRangeSeekBarListener(object : OnRangeSeekBarEvent {
            override fun onCreate(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
            }

            override fun onSeek(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
                binding.handlerTop.visibility = View.GONE
                onSeekThumbs(index, value)
            }

            override fun onSeekStart(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
            }

            override fun onSeekStop(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
                onStopSeekThumbs()
            }
        })
    }

    private fun onPlayerIndicatorSeekChanged(progress: Int, fromUser: Boolean) {
        val duration = (mDuration * progress / 1000L)
        if (fromUser) {
            if (duration < mStartPosition) setProgressBarPosition(mStartPosition)
            else if (duration > mEndPosition) setProgressBarPosition(mEndPosition)
        }
    }

    private fun onPlayerIndicatorSeekStart() {
        mMessageHandler.removeMessages(SHOW_PROGRESS)
        mPlayer.pause()
        binding.iconVideoPlay.visibility = View.VISIBLE
        notifyProgressUpdate(false)
    }

    private fun onPlayerIndicatorSeekStop(seekBar: SeekBar) {
        mMessageHandler.removeMessages(SHOW_PROGRESS)
        mPlayer.pause()
        binding.iconVideoPlay.visibility = View.VISIBLE

        val duration = (mDuration * seekBar.progress / 1000L).toInt()
        mPlayer.seekTo(duration.toLong())
        notifyProgressUpdate(false)
    }

    private fun setProgressBarPosition(position: Long) {
        if (mDuration > 0) binding.handlerTop.progress = (1000L * position / mDuration).toInt()
    }

    private fun setUpMargins() {
        val marge = binding.timeLineBar.thumbs[0].widthBitmap
        val lp = binding.timeLineView.layoutParams as LayoutParams
        lp.setMargins(marge, 0, marge, 0)
        binding.timeLineView.layoutParams = lp
    }



    private fun onClickVideoPlayPause() {
        if ( mPlayer.isPlaying) {
            binding.iconVideoPlay.visibility = View.VISIBLE
            mMessageHandler.removeMessages(SHOW_PROGRESS)
            mPlayer.pause()
        } else {
            binding.iconVideoPlay.visibility = View.GONE
            if (mResetSeekBar) {
                mResetSeekBar = false
                mPlayer.seekTo(mStartPosition)
            }
            mResetSeekBar = false
            mMessageHandler.sendEmptyMessage(SHOW_PROGRESS)
            mPlayer.play()
        }
    }

    fun togglePlayPause() {
        onClickVideoPlayPause()
    }

    fun skipBackward(ms: Long) {
        val newPos = (currentPosition - ms).coerceAtLeast(0L)
        mPlayer.seekTo(newPos)
        notifyProgressUpdate(false)
    }

    fun skipForward(ms: Long) {
        val newPos = (currentPosition + ms).coerceAtMost(totalDuration)
        mPlayer.seekTo(newPos)
        notifyProgressUpdate(false)
    }

    fun onCancelClicked() {
        mPlayer.stop()
    }

    private fun onVideoPrepared(mp: ExoPlayer) {
        if (isVideoPrepared) return
        isVideoPrepared = true
        val videoWidth = mp.videoSize.width
        val videoHeight = mp.videoSize.height
        val videoProportion = videoWidth.toFloat() / videoHeight.toFloat()
        val screenWidth = binding.layoutSurfaceView.width
        val screenHeight = binding.layoutSurfaceView.height
        val screenProportion = screenWidth.toFloat() / screenHeight.toFloat()
        val lp = binding.videoLoader.layoutParams

        if (videoProportion > screenProportion) {
            lp.width = screenWidth
            lp.height = (screenWidth.toFloat() / videoProportion).toInt()
        } else {
            lp.width = (videoProportion * screenHeight.toFloat()).toInt()
            lp.height = screenHeight
        }
        videoPlayerWidth = lp.width
        videoPlayerHeight = lp.height
        binding.videoLoader.layoutParams = lp

        binding.iconVideoPlay.visibility = View.VISIBLE
        mDuration =  mPlayer.duration
//        mDuration = binding.videoLoader.duration.toFloat()
        setSeekBarPosition()
        setTimeFrames()
//        mOnVideoListener?.onVideoPrepared()

    }

    private fun setSeekBarPosition() {
        when {
            mDuration >= mMaxDuration && mMaxDuration != -1 -> {
                mStartPosition = mDuration / 2 - mMaxDuration / 2
                mEndPosition = mDuration / 2 + mMaxDuration / 2
                binding.timeLineBar.setThumbValue(0, (mStartPosition * 100 / mDuration))
                binding.timeLineBar.setThumbValue(1, (mEndPosition * 100 / mDuration))
            }

            mDuration <= mMinDuration && mMinDuration != -1 -> {
                mStartPosition = mDuration / 2 - mMinDuration / 2
                mEndPosition = mDuration / 2 + mMinDuration / 2
                binding.timeLineBar.setThumbValue(0, (mStartPosition * 100 / mDuration))
                binding.timeLineBar.setThumbValue(1, (mEndPosition * 100 / mDuration))
            }

            else -> {
                mStartPosition = 0L
                mEndPosition = mDuration
            }
        }
        mPlayer.seekTo(mStartPosition)
        //binding.videoLoader.seekTo(mStartPosition.toInt())
        mTimeVideo = mDuration
        binding.timeLineBar.initMaxWidth()
    }

    private fun setTimeFrames() {
        val seconds = context.getString(R.string.short_seconds)
        binding.textTimeSelection.text = String.format(
            Locale.ENGLISH,
            "%s %s - %s %s",
            TrimVideoUtils.stringForTime(mStartPosition),
            seconds,
            TrimVideoUtils.stringForTime(mEndPosition),
            seconds
        )
    }

    private fun onSeekThumbs(index: Int, value: Float) {
        when (index) {
            Thumb.LEFT -> {
                mStartPosition = ((mDuration * value / 100L).toLong())
                mPlayer.seekTo(mStartPosition)
//                binding.videoLoader.seekTo(mStartPosition.toInt())
            }

            Thumb.RIGHT -> {
                mEndPosition = ((mDuration * value / 100L).toLong())
            }
        }
        setTimeFrames()
        mTimeVideo = mEndPosition - mStartPosition
    }

    private fun onStopSeekThumbs() {
        mMessageHandler.removeMessages(SHOW_PROGRESS)
        mPlayer.pause()
//        binding.videoLoader.pause()
        binding.iconVideoPlay.visibility = View.VISIBLE
    }

//    private fun onVideoCompleted() {
//        mPlayer.seekTo(mStartPosition)
////        binding.videoLoader.seekTo(mStartPosition.toInt())
//    }

    private fun notifyProgressUpdate(all: Boolean) {
        if (mDuration == 0L) return
        val position =  mPlayer.currentPosition//binding.videoLoader.currentPosition
        if (all) {
            for (item in mListeners) {
                item.updateProgress(position.toFloat(), mDuration, (position * 100 / mDuration))
            }
        } else {
            mListeners[0].updateProgress(
                position.toFloat(),
                mDuration,
                (position * 100 / mDuration)
            )
        }
    }

    private fun updateVideoProgress(time: Long) {
        if (binding.videoLoader == null) return
        if (time <= mStartPosition && time <= mEndPosition) binding.handlerTop.visibility =
            View.GONE
        else binding.handlerTop.visibility = View.VISIBLE
        if (time >= mEndPosition) {
            mMessageHandler.removeMessages(SHOW_PROGRESS)
            mPlayer.pause()
//            binding.videoLoader.pause()
            binding.iconVideoPlay.visibility = View.VISIBLE
            mResetSeekBar = true
            return
        }
        setProgressBarPosition(time)
    }

    fun setVideoBackgroundColor(@ColorInt color:Int) = with(binding){
        container.setBackgroundColor(color)
        layout.setBackgroundColor(color)
    }

    fun setFrameColor(@ColorInt color: Int) = with(binding){
        frameColor.setBackgroundColor(color)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun saveVideo() {
        val txtTime = binding.textTimeSelection.text.toString()
        val pattern = "\\d{2}:\\d{2}"
        val regex = Regex(pattern)
        val matches = regex.findAll(txtTime)
        val timeList = matches.map { it.value }.toList()

        val filePath  = "$destinationPath/${UUID.randomUUID()}.mp4"

        val transformation = TransformationRequest.Builder()
            .setVideoMimeType(MimeTypes.VIDEO_H264)
            .setAudioMimeType(MimeTypes.AUDIO_AAC)
            //.setHdrMode()
            .build()
        val transformer = androidx.media3.transformer.Transformer.Builder(context)
            .setTransformationRequest(transformation)
            .addListener(object : androidx.media3.transformer.Transformer.Listener {
                override fun onCompleted(composition: Composition, exportResult: ExportResult) {
                 mOnVideoEditedListener?.getResult(Uri.parse(filePath))
                }

                override fun onError(
                    composition: Composition,
                    exportResult: ExportResult,
                    exportException: ExportException
                ) {
                    exportException.localizedMessage?.let { mOnVideoEditedListener?.onError(it) }
                }
            })
            .build()

        val startMilliseconds = timeToMilliseconds(timeList[0])
        val endMilliseconds = timeToMilliseconds(timeList[1])


        val inputMediaItem = MediaItem.Builder()
            .setUri(mSrc)
            .setClippingConfiguration(
                MediaItem.ClippingConfiguration.Builder()
                    .setStartPositionMs(startMilliseconds)
                    .setEndPositionMs(endMilliseconds)
                    .build()
            )
            .build()

        /**
         * set effects
         */
        val editedMediaItem = EditedMediaItem.Builder(inputMediaItem)
            .setFrameRate(30)
            .setEffects(Effects.EMPTY)
            .build()

        val composition = Composition.Builder(listOf(EditedMediaItemSequence(listOf(editedMediaItem))))
            .build()

        transformer.start(composition, filePath)
    }

    private fun timeToMilliseconds(time: String): Long {
        val parts = time.split(":")
        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid time format: $time")
        }

        val minutes = parts[0].toInt()
        val seconds = parts[1].trim().split(" ")[0].toInt() // Extract seconds

        // Calculate total milliseconds
        return ((minutes * 60 + seconds) * 1000).toLong()
    }

    fun setBitrate(bitRate: Int): VideoEditor {
        this.bitRate = bitRate
        return this
    }

    fun setVideoInformationVisibility(visible: Boolean): VideoEditor {
        binding.timeFrame.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setOnTrimVideoListener(onVideoEditedListener: OnVideoEditedEvent): VideoEditor {
        mOnVideoEditedListener = onVideoEditedListener
        return this
    }

//    fun setOnVideoListener(onVideoListener: OnVideoEvent): VideoEditor {
//        mOnVideoListener = onVideoListener
//        return this
//    }

    fun destroy() {
        BackgroundExecutor.cancelAll("", true)
        UiThreadExecutor.cancelAll("")
    }

    fun setMaxDuration(maxDuration: Int): VideoEditor {
        mMaxDuration = maxDuration * 1000
        return this
    }

    fun setMinDuration(minDuration: Int): VideoEditor {
        mMinDuration = minDuration * 1000
        return this
    }

    fun setDestinationPath(path: String): VideoEditor {
        destinationPath = path
        return this
    }


    @SuppressLint("UnsafeOptInUsageError")
    fun setVideoURI(videoURI: Uri): VideoEditor {
        mSrc = videoURI

        /**
         * setup video player
         */
        mPlayer = ExoPlayer.Builder(context)
            .build()

        val dataSourceFactory = DefaultDataSource.Factory(context)
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURI))

        mPlayer.setMediaSource(videoSource)
        mPlayer.prepare()
        mPlayer.playWhenReady = false

        binding.videoLoader.also {
            it.player = mPlayer
            it.useController = false
        }


        mPlayer.addListener(object :Player.Listener{
            override fun onPlayerError(error: PlaybackException) {
                mOnVideoEditedListener?.onError("Something went wrong reason : ${error.localizedMessage}")
            }
            @SuppressLint("UnsafeOptInUsageError")
            override fun onVideoSizeChanged(videoSize: VideoSize) {
                super.onVideoSizeChanged(videoSize)
                if (mPlayer.videoSize.width > mPlayer.videoSize.height) {
                    binding.videoLoader.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
                } else {
                    binding.videoLoader.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    mPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                }

                onVideoPrepared(mPlayer)
            }
        })

        binding.videoLoader.requestFocus()
        binding.timeLineView.setVideo(mSrc)
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, mSrc)
        val metaDateWidth =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                ?.toInt() ?: 0
        val metaDataHeight =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                ?.toInt() ?: 0

        //If the rotation is 90 or 270 the width and height will be transposed.
        when (mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
            ?.toInt()) {
            90, 270 -> {
                originalVideoWidth = metaDataHeight
                originalVideoHeight = metaDateWidth
            }

            else -> {
                originalVideoWidth = metaDateWidth
                originalVideoHeight = metaDataHeight
            }
        }

        return this
    }

//    private fun setupCropper(width: Int, height: Int) {
//        binding.cropFrame.setFixedAspectRatio(false)
//        binding.cropFrame.layoutParams = binding.cropFrame.layoutParams?.let {
//            it.width = width
//            it.height = height
//            it
//        }
//        binding.cropFrame.setImageBitmap(
//            context.getDrawable(android.R.color.transparent)
//                ?.let { drawableToBitmap(it, width, height) })
//    }

    fun setTextTimeSelectionTypeface(tf: Typeface?): VideoEditor {
        if (tf != null) binding.textTimeSelection.typeface = tf
        return this
    }

    fun onResume() {
        mPlayer.seekTo(videoPlayerCurrentPosition)
//        binding.videoLoader.seekTo(videoPlayerCurrentPosition)
    }

    fun onPause() {
        videoPlayerCurrentPosition =  mPlayer.currentPosition
//        videoPlayerCurrentPosition = binding.videoLoader.currentPosition
    }

    private class MessageHandler(view: VideoEditor) : Handler(Looper.getMainLooper()) {
        private val mView: WeakReference<VideoEditor> = WeakReference(view)
        override fun handleMessage(msg: Message) {
            val view = mView.get()
            if (view == null || view.binding.videoLoader == null) return
            view.notifyProgressUpdate(true)
            if (view.binding.videoLoader.player?.isPlaying == true) sendEmptyMessageDelayed(0, 10)
        }
    }

    companion object {
        private const val MIN_TIME_FRAME = 1000
        private const val SHOW_PROGRESS = 2
        private const val MIN_BITRATE = 1500000.0

    }
}

private fun Double.bitToMb() = this / 1000000