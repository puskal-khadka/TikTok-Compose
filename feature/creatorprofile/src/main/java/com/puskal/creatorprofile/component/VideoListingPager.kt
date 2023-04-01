package com.puskal.creatorprofile.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.puskal.creatorprofile.screen.creatorprofile.CreatorProfileViewModel
import com.puskal.creatorprofile.screen.creatorprofile.ProfilePagerTabs
import com.puskal.creatorprofile.screen.creatorprofile.tabs.LikeVideoTab
import com.puskal.creatorprofile.screen.creatorprofile.tabs.PublicVideoTab
import com.puskal.data.model.VideoModel
import com.puskal.theme.Black
import com.puskal.theme.Gray
import com.puskal.theme.SeparatorColor
import kotlinx.coroutines.launch

/**
 * Created by Puskal Khadka on 3/23/2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoListingPager(
    scrollState: ScrollState,
    height: Dp,
    viewModel: CreatorProfileViewModel,
    onClickVideo: (video: VideoModel, index: Int) -> Unit
) {
    val pagerState = rememberPagerState()
    val tabs = ProfilePagerTabs.values().asList()
    val coroutineScope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LazyColumn(modifier = Modifier
        .height(height)
        .nestedScroll(
            remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        return if (available.y > 0) Offset.Zero else
                            Offset(x = 0f, y = -scrollState.dispatchRawDelta(-available.y))
                    }
                }
            }
        )) {
        item {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    val modifier = Modifier
                        .tabIndicatorOffset(it[pagerState.currentPage])
                        .padding(horizontal = screenWidth.div(5.5f))

                    TabRowDefaults.Indicator(
                        modifier, color = Black
                    )
                },
                divider = {
                    Divider(thickness = 0.5.dp, color = SeparatorColor)
                },
            ) {
                tabs.forEachIndexed { index, item ->
                    val isSelected = pagerState.currentPage == index

                    Tab(
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        selectedContentColor = Color.Transparent,
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = if (isSelected) Black else Gray
                            )
                        }
                    )
                }
            }
        }
        item {
            HorizontalPager(
                pageCount = tabs.size, state = pagerState
            ) {
                when (it) {
                    0 -> {
                        PublicVideoTab(viewModel, scrollState, onClickVideo = onClickVideo)
                    }
                    1 -> {
                        LikeVideoTab(viewModel)
                    }
                }
            }
        }

    }
}







