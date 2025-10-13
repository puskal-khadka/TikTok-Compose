package com.puskal.cameramedia.edit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Toolbar items for trimming screen
 */
enum class TrimTool(val icon: ImageVector) {
    TRIM(Icons.Filled.ContentCut),
    SOUND(Icons.Filled.MusicNote),
    EFFECT(Icons.Filled.Wallpaper),
    TEXT(Icons.Filled.TextFields),
    STICKER(Icons.Filled.EmojiEmotions)
}
