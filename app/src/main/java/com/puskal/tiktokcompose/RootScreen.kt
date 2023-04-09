package com.puskal.tiktokcompose

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.puskal.core.DestinationRoute.AUTHENTICATION_ROUTE
import com.puskal.core.DestinationRoute.CAMERA_ROUTE
import com.puskal.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE
import com.puskal.core.DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE
import com.puskal.core.DestinationRoute.FRIENDS_ROUTE
import com.puskal.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.puskal.core.DestinationRoute.INBOX_ROUTE
import com.puskal.core.DestinationRoute.MY_PROFILE_ROUTE
import com.puskal.theme.TikTokTheme
import com.puskal.tiktokcompose.component.BottomBar
import com.puskal.tiktokcompose.navigation.AppNavHost

/**
 * Created by Puskal Khadka on 3/14/2023.
 */
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun RootScreen() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination
    val context= LocalContext.current


    val isShowBottomBar = when (currentDestination?.route) {
        HOME_SCREEN_ROUTE, INBOX_ROUTE, COMMENT_BOTTOM_SHEET_ROUTE,
        FRIENDS_ROUTE, AUTHENTICATION_ROUTE, MY_PROFILE_ROUTE, null -> true
        else -> false
    }
    val darkMode = when (currentDestination?.route) {
        HOME_SCREEN_ROUTE, FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE, CAMERA_ROUTE, null -> true
        else -> false
    }

    if(currentDestination?.route== HOME_SCREEN_ROUTE){
       BackHandler {
           (context as? Activity)?.finish()
       }
    }

    TikTokTheme(darkTheme = darkMode) {
        SetupSystemUi(rememberSystemUiController(), MaterialTheme.colorScheme.background)
        ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
            Scaffold(
                topBar = {

                },
                bottomBar = {
                    if (!isShowBottomBar) {
                        return@Scaffold
                    }
                    BottomBar(navController, currentDestination, isDarkTheme = darkMode)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    AppNavHost(navController = navController)
                }
            }
        }


    }
}

@Composable
fun SetupSystemUi(
    systemUiController: SystemUiController,
    systemBarColor: Color
) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }
}

@ExperimentalMaterialNavigationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetNavigator(
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    skipHalfExpanded: Boolean = true,
): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        animationSpec,
        skipHalfExpanded,
    )
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}