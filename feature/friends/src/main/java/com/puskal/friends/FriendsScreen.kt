package com.puskal.friends

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.puskal.composable.TopBar
import com.puskal.core.DestinationRoute.AUTHENTICATION_ROUTE
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 4/1/2023.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavController) {
    Scaffold(topBar = {
        TopBar(
            navIcon = null,
            title = stringResource(id = R.string.friends)
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

        }
    }

    LaunchedEffect(key1 = Unit) {
        //for now:- (default guest user) redirect to auth
        navController.apply {
            popBackStack()
            navController.navigate(AUTHENTICATION_ROUTE)
        }
    }
}


