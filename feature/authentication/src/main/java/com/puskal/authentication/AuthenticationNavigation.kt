package com.puskal.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.puskal.core.DestinationRoute.AUTHENTICATION_ROUTE


/**
 * Created by Puskal Khadka on 3/27/2023.
 */

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.authenticationNavGraph(navController: NavController) {
    bottomSheet(route = AUTHENTICATION_ROUTE) {
        AuthenticationScreen(navController)
    }
}