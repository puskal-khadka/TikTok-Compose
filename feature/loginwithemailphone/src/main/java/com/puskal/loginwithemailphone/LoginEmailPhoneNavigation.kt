package com.puskal.loginwithemailphone

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.puskal.core.DestinationRoute

/**
 * Created by Puskal Khadka on 3/27/2023.
 */
fun NavGraphBuilder.loginEmailPhoneNavGraph(navController: NavController) {
    composable(route = DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_EMAIL_ROUTE) {
        LoginWithEmailPhoneScreen(navController)
    }
}