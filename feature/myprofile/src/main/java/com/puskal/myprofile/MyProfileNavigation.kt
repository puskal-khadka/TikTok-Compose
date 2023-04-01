package com.puskal.myprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.puskal.core.DestinationRoute

/**
 * Created by Puskal Khadka on 4/1/2023.
 */
fun NavGraphBuilder.myProfileNavGraph(navController: NavController) {
    composable(route = DestinationRoute.MY_PROFILE_ROUTE) {
        MyProfileScreen(navController)
    }
}