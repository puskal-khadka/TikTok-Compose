package com.puskal.cameramedia

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.runtime.Composable
import com.puskal.data.model.TemplateModel
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 4/3/2023.
 */
data class ViewState(
    val templates: List<TemplateModel>? = null
)

sealed class CameraMediaEvent {
    object EventFetchTemplate : CameraMediaEvent()

}

enum class Tabs(@StringRes val rawValue: Int) {
    CAMERA(rawValue = R.string.camera),
    STORY(rawValue = R.string.story),
    TEMPLATES(rawValue = R.string.templates)
}

enum class PermissionType {
    CAMERA,
    MICROPHONE
}

enum class CameraController(
    @StringRes val title: Int,
    val iconRes: Int? = null,
    val iconVector: ImageVector? = null,
) {
    FLIP(title = R.string.flip, iconRes = R.drawable.ic_flip),
    SPEED(title = R.string.speed, iconRes = R.drawable.ic_speed),
    BEAUTY(title = R.string.beauty, iconRes = R.drawable.ic_profile_fill),
    FILTERS(title = R.string.filters, iconVector = Icons.Filled.InvertColors),
    TIMER(title = R.string.timer, iconRes = R.drawable.ic_timer),
    FLASH(title = R.string.flash, iconRes = R.drawable.ic_flash),
}

@Composable
fun CameraController.icon(): ImageVector {
    iconVector?.let { return it }
    val resId = iconRes ?: error("No icon defined for $this")
    return ImageVector.vectorResource(resId)
}

enum class CameraCaptureOptions(val value: String) {
    SIXTY_SECOND("60s"),
    FIFTEEN_SECOND("15s"),
    PHOTO("Photo"),
    VIDEO("Video"),
    TEXT("Text"),
}

