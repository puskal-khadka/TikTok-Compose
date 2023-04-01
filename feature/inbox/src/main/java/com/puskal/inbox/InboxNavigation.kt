package com.puskal.inbox

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.puskal.core.DestinationRoute.INBOX_ROUTE

/**
 * Created by Puskal Khadka on 3/27/2023.
 */
fun NavGraphBuilder.inboxNavGraph(navController: NavController) {
    composable(route = INBOX_ROUTE) {
        InboxScreen(navController)
    }
}