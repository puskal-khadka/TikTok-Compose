package com.puskal.cameramedia.edit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material.icons.outlined.Star
import androidx.annotation.StringRes
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.ui.graphics.vector.ImageVector
import com.puskal.theme.R

enum class VideoEditTool(@StringRes val title: Int, val icon: ImageVector) {
    SETTINGS(R.string.settings, Icons.Filled.Settings),
    SHARE(R.string.share, Icons.Filled.Share),
    TRIM(R.string.trim, Icons.Filled.ContentCut),
    TEXT(R.string.text, Icons.Filled.TextFields),
    CHALLENGE(R.string.challenge, Icons.Filled.Flag),
    STICKERS(R.string.stickers, Icons.Filled.StickyNote2),
    EFFECTS(R.string.effects, Icons.Filled.Animation),
    MORE(R.string.see_more, Icons.Filled.KeyboardArrowDown),
    FILTERS(R.string.filters, Icons.Filled.InvertColors),
    BEAUTY(R.string.beauty, Icons.Filled.Face),
    CROP_RESIZE(R.string.crop_resize, Icons.Filled.Crop)
}

enum class ResizeMenuFeature(@StringRes val title: Int, val icon: ImageVector) {
    AUTO_SUBTITLES(R.string.audio_transcript, Icons.Outlined.Subtitles),
    QUALITY_ENHANCEMENT(R.string.enhance_video_quality, Icons.Outlined.Star),
    VOICE_CHANGER(R.string.change_voice, Icons.Outlined.Mic),
    BRUSH_TOOL(R.string.drawing_painter, Icons.Outlined.Brush),
    COLLAPSE_TOOLBAR(R.string.hide, Icons.Filled.KeyboardArrowUp)
}
