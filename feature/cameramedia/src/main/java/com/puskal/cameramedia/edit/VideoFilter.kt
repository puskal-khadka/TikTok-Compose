package com.puskal.cameramedia.edit

import com.daasuu.mp4compose.filter.*

/**
 * List of sample video filters backed by mp4compose [GlFilter] implementations.
 */
enum class VideoFilter(val title: String, val create: () -> GlFilter) {
    NONE("None", { GlFilter() }),
    GRAY("Gray", { GlGrayScaleFilter() }),
    SEPIA("Sepia", { GlSepiaFilter() }),
    INVERT("Invert", { GlInvertFilter() }),
    BILATERAL("Bilateral", { GlBilateralFilter() }),
    BOX_BLUR("Box Blur", { GlBoxBlurFilter() }),
    CONTRAST("Contrast", { GlContrastFilter() }),
    GAUSSIAN_BLUR("Gaussian Blur", { GlGaussianBlurFilter() }),
    VIGNETTE("Vignette", { GlVignetteFilter() }),
    PIXELATION("Pixelation", { GlPixelationFilter() });
}
