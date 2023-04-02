package com.puskal.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.puskal.composable.TopBar
import com.puskal.core.DestinationRoute.AUTHENTICATION_ROUTE
import com.puskal.core.extension.MediumSpace
import com.puskal.core.extension.Space
import com.puskal.theme.*
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 4/1/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val viewState by settingViewModel.viewState.collectAsState()

    val scrollState = rememberScrollState()

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            scrollState.value.dp > expandedTitleHeight
        }
    }

    Scaffold(
        topBar = { TopBar(title = if (isCollapsed) stringResource(id = R.string.settings_and_privacy) else "") { navController.navigateUp() } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteLightDimBg)
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.settings_and_privacy),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            )
            MediumSpace()
            viewState?.settingUiData?.let {
                it.forEach {
                    GroupedUiCardSection(item = it.key to it.value, onClickItem = { titleId ->
                        when (titleId) {
                            R.string.my_account -> navController.navigate(AUTHENTICATION_ROUTE)
                        }
                    })
                }
            }
            22.dp.Space()
            Text(
                text = "v()",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth(),
                color = SubTextColor
            )
            16.dp.Space()
        }
    }

}

@Composable
fun GroupedUiCardSection(item: Pair<String, List<RowItem>>, onClickItem: (Int) -> Unit) {
    Text(
        text = item.first,
        modifier = Modifier.padding(horizontal = 20.dp),
        color = SubTextColor,
        style = MaterialTheme.typography.labelMedium
    )
    8.dp.Space()
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding(horizontal = 8.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 18.dp, end = 16.dp)
                .padding(vertical = 8.dp)
        ) {
            item.second.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickItem(it.title) }
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null,
                        modifier = Modifier.size(19.dp),
                        tint = Gray
                    )
                    Text(
                        text = stringResource(id = it.title),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (it.title == R.string.my_account) {
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            style = MaterialTheme.typography.labelMedium,
                            color = White,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(2.dp)
                                )
                                .padding(horizontal = 22.dp, vertical = 4.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_left_arrow),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
    MediumSpace()
}

private val expandedTitleHeight = 132.dp