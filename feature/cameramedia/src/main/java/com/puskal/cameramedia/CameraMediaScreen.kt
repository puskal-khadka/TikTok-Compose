package com.puskal.cameramedia

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.puskal.cameramedia.tabs.CameraScreen
import com.puskal.cameramedia.tabs.TemplateScreen
import com.puskal.core.extension.getCurrentBrightness
import com.puskal.core.utils.DisableRippleInteractionSource
import com.puskal.theme.White
import kotlinx.coroutines.launch


/**
 * Created by Puskal Khadka on 4/2/2023.
 */


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CameraMediaScreen(
    navController: NavController,
    cameraMediaViewModel: CameraMediaViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = Tabs.values().asList()
    val context = LocalContext.current
    val minimumScreenBrightness = 0.25f

    DisposableEffect(key1 = Unit) {
        val attrs = (context as Activity).window.attributes.apply {
            if (context.getCurrentBrightness() < minimumScreenBrightness) {
                screenBrightness = minimumScreenBrightness
            }
        }
        context.window.attributes = attrs
        onDispose {
            context.window.attributes = attrs.apply {
                screenBrightness = context.getCurrentBrightness()
            }
        }
    }


    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(
                    pageCount = tabs.size,
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0, 1 -> CameraScreen(
                            navController = navController,
                            viewModel = cameraMediaViewModel,
                            cameraOpenType = tabs[page]
                        )
                        2 -> TemplateScreen(
                            navController = navController,
                            viewModel = cameraMediaViewModel,
                        )
                    }
                }
            }
            BottomTabLayout(pagerState) {
                coroutineScope.launch {
                    pagerState.scrollToPage(it)
                }
            }

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomTabLayout(
    pagerState: PagerState,
    onClickTab: (position: Int) -> Unit
) {
    val edgePadding = LocalConfiguration.current.screenWidthDp.div(2).dp
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {},
        indicator = {},
        edgePadding = edgePadding
    ) {
        Tabs.values().asList().forEachIndexed { index, tab ->
            val isSelected = pagerState.currentPage == index
            Tab(selected = isSelected, onClick = {
                onClickTab(index)
            }, interactionSource = remember { DisableRippleInteractionSource() }, text = {
                val textColor = if (isSelected) White
                else White.copy(alpha = 0.6f)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = tab.rawValue),
                        style = MaterialTheme.typography.labelLarge,
                        color = textColor
                    )
                    Box(
                        modifier = Modifier
                            .alpha(if (isSelected) 1f else 0f)
                            .padding(top = 10.dp)
                            .size(5.dp)
                            .background(color = White, shape = CircleShape)
                    )
                }
            })
        }
    }
}



