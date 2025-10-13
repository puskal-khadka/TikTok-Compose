package com.puskal.commentlisting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.puskal.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE
import com.puskal.commentlisting.CommentListViewModel

/**
 * Created by Puskal Khadka on 3/22/2023.
 */

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.commentListingNavGraph(navController: NavController) {
    bottomSheet(route = COMMENT_BOTTOM_SHEET_ROUTE) { backStackEntry ->
        val viewModel: CommentListViewModel = hiltViewModel(backStackEntry)
        CommentListScreen(
            viewModel = viewModel,
            onClickCancel = { navController.navigateUp() }
        )
    }
}