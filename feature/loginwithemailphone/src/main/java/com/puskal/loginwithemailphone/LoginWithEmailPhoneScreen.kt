package com.puskal.loginwithemailphone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.puskal.composable.TopBar
import com.puskal.loginwithemailphone.tabs.EmailUsernameTabScreen
import com.puskal.loginwithemailphone.tabs.PhoneTabScreen
import com.puskal.theme.Black
import com.puskal.theme.R
import com.puskal.theme.SeparatorColor
import com.puskal.theme.SubTextColor
import kotlinx.coroutines.launch

/**
 * Created by Puskal Khadka on 3/27/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWithEmailPhoneScreen(
    navController: NavController,
    viewModel: LoginWithEmailPhoneViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopBar(
            title = stringResource(id = R.string.login_or_sign_up),
            actions = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_question_circle),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        ) {
            navController.navigateUp()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LoginPager(viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LoginPager(viewModel: LoginWithEmailPhoneViewModel) {
    val pagerState = rememberPagerState()
    val pages = LoginPages.values().asList()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.settledPage }.collect {
            viewModel.onTriggerEvent(LoginEmailPhoneEvent.EventPageChange(it))
        }
    }
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = {
            val modifier = Modifier
                .tabIndicatorOffset(it[pagerState.currentPage])
                .padding(horizontal = 26.dp)
            TabRowDefaults.Indicator(modifier, color = Black)
        },
        divider = {
            Divider(thickness = 0.5.dp, color = SeparatorColor)
        },
    ) {
        pages.forEachIndexed { index, item ->
            val isSelected = pagerState.currentPage == index
            Tab(
                selected = isSelected,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = SubTextColor,
                text = {
                    Text(
                        text = stringResource(id = item.title),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        pageCount = pages.size,
        key = { it }
    ) { page ->

        when (page) {
            0 -> PhoneTabScreen(viewModel)
            1 -> EmailUsernameTabScreen(viewModel)
        }
    }
}