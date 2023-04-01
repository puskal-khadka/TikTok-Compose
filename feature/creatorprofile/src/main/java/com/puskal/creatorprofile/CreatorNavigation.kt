package com.puskal.creatorprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.puskal.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE
import com.puskal.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.puskal.core.DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE
import com.puskal.core.DestinationRoute.PassedKey.USER_ID
import com.puskal.core.DestinationRoute.PassedKey.VIDEO_INDEX
import com.puskal.creatorprofile.screen.creatorprofile.CreatorProfileScreen
import com.puskal.creatorprofile.screen.creatorvideo.CreatorVideoPagerScreen

/**
 * Created by Puskal Khadka on 3/22/2023.
 */

fun NavGraphBuilder.creatorProfileNavGraph(navController: NavController) {
    composable(route = "$CREATOR_PROFILE_ROUTE/{$USER_ID}",
        arguments = listOf(
            navArgument(USER_ID) { type = NavType.LongType }
        )
    ) {
        CreatorProfileScreen(
            onClickNavIcon = { navController.navigateUp() },
            navController = navController
        )
    }

    composable(route = FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE,
        arguments = listOf(
            navArgument(USER_ID) { type = NavType.LongType },
            navArgument(VIDEO_INDEX) { type = NavType.IntType }
        )
    ) {
        CreatorVideoPagerScreen(
            onClickNavIcon = { navController.navigateUp() },
            onclickComment = { navController.navigate(COMMENT_BOTTOM_SHEET_ROUTE) },
            onClickAudio = {},
            onClickUser = { navController.navigateUp() }
        )
    }
}

