package com.puskal.tiktokcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.puskal.authentication.authenticationNavGraph
import com.puskal.cameramedia.cameraMediaNavGraph
import com.puskal.commentlisting.commentListingNavGraph
import com.puskal.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.puskal.creatorprofile.creatorProfileNavGraph
import com.puskal.friends.friendsNavGraph
import com.puskal.home.homeNavGraph
import com.puskal.inbox.inboxNavGraph
import com.puskal.loginwithemailphone.loginEmailPhoneNavGraph
import com.puskal.myprofile.myProfileNavGraph
import com.puskal.setting.settingNavGraph

/**
 * Created by Puskal Khadka on 3/14/2023.
 */

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_SCREEN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeNavGraph(navController)
        commentListingNavGraph(navController)
        creatorProfileNavGraph(navController)
        inboxNavGraph(navController)
        authenticationNavGraph(navController)
        loginEmailPhoneNavGraph(navController)
        friendsNavGraph(navController)
        myProfileNavGraph(navController)
        settingNavGraph(navController)
        cameraMediaNavGraph(navController)
    }
}