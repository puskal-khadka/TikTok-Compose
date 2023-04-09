package com.puskal.cameramedia.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.puskal.cameramedia.CameraMediaEvent
import com.puskal.cameramedia.CameraMediaViewModel
import com.puskal.composable.CustomButton
import com.puskal.core.extension.LargeSpace
import com.puskal.core.extension.MediumSpace
import com.puskal.core.extension.SmallSpace
import com.puskal.core.extension.Space
import com.puskal.data.model.TemplateModel
import com.puskal.theme.R
import com.puskal.theme.SubTextColor
import kotlin.math.absoluteValue

/**
 * Created by Puskal Khadka on 4/2/2023.
 */

@Composable
fun TemplateScreen(
    navController: NavController,
    viewModel: CameraMediaViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onTriggerEvent(CameraMediaEvent.EventFetchTemplate)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 20.dp, start = 6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            viewState?.templates?.let {
                TemplatePager(it)
            }
        }
        CustomButton(
            buttonText = stringResource(id = R.string.upload_photos),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {

        }
        LargeSpace()
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TemplatePager(templates: List<TemplateModel>) {
    val pagerState = rememberPagerState()

    val currentItem by remember {
        derivedStateOf {
            pagerState.settledPage
        }
    }

    Text(text = templates[currentItem].name, style = MaterialTheme.typography.displayMedium)
    6.dp.Space()
    Text(
        text = templates[currentItem].hint,
        style = MaterialTheme.typography.labelLarge,
        color = SubTextColor
    )
    MediumSpace()
    HorizontalPager(
        pageCount = templates.size,
        contentPadding = PaddingValues(horizontal = 64.dp),
        state = pagerState,
        modifier = Modifier.weight(1f)
    ) {
        SingleTemplateCard(page = it, pagerState = pagerState, item = templates[it])
    }
    SmallSpace()
    Text(
        text = "${currentItem.plus(1)}/${templates.size}",
        color = SubTextColor,
        style = MaterialTheme.typography.labelMedium
    )
    SmallSpace()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleTemplateCard(
    page: Int,
    pagerState: PagerState,
    item: TemplateModel,
) {
    val pageOffset =
        ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.82f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            Modifier
                .blur(if (pagerState.settledPage == page) 0.dp else 60.dp)
        )
        {
            AsyncImage(
                model = item.parseMediaLink(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}