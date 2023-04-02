package com.puskal.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 4/1/2023.
 */

data class ViewState(
    val settingUiData: Map<String, List<RowItem>>? = null
)

sealed class SettingEvent {

}


data class RowItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val rightSideClickItem: @Composable (() -> Unit)? = null,
)

val settingUiModel: Map<String, List<RowItem>> = mapOf(
    "Account" to listOf(
        RowItem(icon = R.drawable.ic_profile_fill, title = R.string.my_account)
    ),
    "Content & Display" to listOf(
        RowItem(icon = R.drawable.ic_tv, title = R.string.live),
        RowItem(icon = R.drawable.ic_playback, title = R.string.playback),
        RowItem(icon = R.drawable.ic_language, title = R.string.language)
    ),
    "Cache & Cellular" to listOf(
        RowItem(icon = R.drawable.ic_recycler_bin, title = R.string.free_up_space),
        RowItem(icon = R.drawable.ic_data_saver, title = R.string.data_saver),
        RowItem(icon = R.drawable.ic_wallpaper, title = R.string.wallpaper)
    ),
    "Support & About" to listOf(
        RowItem(icon = R.drawable.ic_flag, title = R.string.report_a_problem),
        RowItem(icon = R.drawable.ic_support, title = R.string.support),
        RowItem(icon = R.drawable.ic_info, title = R.string.terms_and_policies)
    )
)