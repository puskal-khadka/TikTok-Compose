package com.redevrx.video_trimmer.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.redevrx.video_trimmer.R
import com.redevrx.video_trimmer.event.OnProgressVideoEvent
import com.redevrx.video_trimmer.event.OnRangeSeekBarEvent


class ProgressBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr),
    OnRangeSeekBarEvent, OnProgressVideoEvent {

    private var mProgressHeight: Int = 0
    private var mViewWidth: Int = 0

    private val mBackgroundColor = Paint()
    private val mProgressColor = Paint()

    private var mBackgroundRect: Rect? = null
    private var mProgressRect: Rect? = null

    init {
        init()
    }

    private fun init() {
        val lineProgress = context.getColor(R.color.progress_color)
        val lineBackground = context.getColor(R.color.background_progress_color)

        mProgressHeight = context.resources.getDimensionPixelOffset(R.dimen.progress_video_line_height)

        mBackgroundColor.isAntiAlias = true
        mBackgroundColor.color = lineBackground

        mProgressColor.isAntiAlias = true
        mProgressColor.color = lineProgress
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minW = paddingLeft + paddingRight + suggestedMinimumWidth
        mViewWidth = resolveSizeAndState(minW, widthMeasureSpec, 1)

        val minH = paddingBottom + paddingTop + mProgressHeight
        val viewHeight = resolveSizeAndState(minH, heightMeasureSpec, 1)

        setMeasuredDimension(mViewWidth, viewHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawLineBackground(canvas)
        drawLineProgress(canvas)
    }

    private fun drawLineBackground(canvas: Canvas) {
        if (mBackgroundRect != null) {
            canvas.drawRect(mBackgroundRect!!, mBackgroundColor)
        }
    }

    private fun drawLineProgress(canvas: Canvas) {
        if (mProgressRect != null) {
            canvas.drawRect(mProgressRect!!, mProgressColor)
        }
    }

    override fun onCreate(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
        updateBackgroundRect(index, value)
    }

    override fun onSeek(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
        updateBackgroundRect(index, value)
    }

    override fun onSeekStart(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
        updateBackgroundRect(index, value)
    }

    override fun onSeekStop(rangeSeekBarView: RangeSeekBarView, index: Int, value: Float) {
        updateBackgroundRect(index, value)
    }

    private fun updateBackgroundRect(index: Int, value: Float) {
        if (mBackgroundRect == null) mBackgroundRect = Rect(0, 0, mViewWidth, mProgressHeight)
        val newValue = (mViewWidth * value / 100).toInt()
        mBackgroundRect = if (index == 0) {
            Rect(newValue, mBackgroundRect!!.top, mBackgroundRect!!.right, mBackgroundRect!!.bottom)
        } else {
            Rect(mBackgroundRect!!.left, mBackgroundRect!!.top, newValue, mBackgroundRect!!.bottom)
        }
        updateProgress(0f, 0L, (0.0).toLong())
    }

    override fun updateProgress(time: Float, max: Long, scale: Long) {
        if (mBackgroundRect != null) {
            mProgressRect = if (scale == 0L) {
                Rect(0, mBackgroundRect!!.top, 0, mBackgroundRect!!.bottom)
            } else {
                val newValue = (mViewWidth * scale / 100).toInt()
                Rect(mBackgroundRect!!.left, mBackgroundRect!!.top, newValue, mBackgroundRect!!.bottom)
            }
        }
        invalidate()
    }
}