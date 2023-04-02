package com.puskal.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.puskal.core.DestinationRoute

/**
 * Created by Puskal Khadka on 4/1/2023.
 */
fun NavGraphBuilder.settingNavGraph(navController: NavController) {
    composable(route = DestinationRoute.SETTING_ROUTE) {
        SettingScreen(navController)
    }
}