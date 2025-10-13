package com.redevrx.video_trimmer.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.redevrx.video_trimmer.R
import java.util.*

class Thumb private constructor() {

    var index: Int = 0
        private set
    var value: Float = 0.toFloat()
    var pos: Float = 0.toFloat()
    var bitmap: Bitmap? = null
        private set(bitmap) {
            field = bitmap
            widthBitmap = bitmap?.width ?: 0
            heightBitmap = bitmap?.height ?: 0
        }
    var widthBitmap: Int = 0
        private set
    private var heightBitmap: Int = 0

    var lastTouchX: Float = 0.toFloat()

    init {
        value = 0f
        pos = 0f
    }

    companion object {
        const val LEFT = 0
        const val RIGHT = 1

        fun initThumbs(resources: Resources): List<Thumb> {
            val thumbs = Vector<Thumb>()
            for (i in 0..1) {
                val th = Thumb()
                th.index = i
                if (i == 0) {
                    val resImageLeft = R.drawable.seek_left_handle
                    th.bitmap = BitmapFactory.decodeResource(resources, resImageLeft)
                } else {
                    val resImageRight = R.drawable.seek_right_handle
                    th.bitmap = BitmapFactory.decodeResource(resources, resImageRight)
                }
                thumbs.add(th)
            }
            return thumbs
        }

        fun getWidthBitmap(thumbs: List<Thumb>): Int = thumbs[0].widthBitmap

        fun getHeightBitmap(thumbs: List<Thumb>): Int = thumbs[0].heightBitmap

        fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable && drawable.bitmap != null) return drawable.bitmap
            val bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            else Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            if (bitmap != null) {
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
            return bitmap
        }
    }
}