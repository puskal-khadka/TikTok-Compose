package com.puskal.creatorprofile.screen.creatorprofile.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.puskal.core.extension.Space
import com.puskal.creatorprofile.screen.creatorprofile.CreatorProfileViewModel
import com.puskal.theme.Gray
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 3/25/2023.
 */

@Composable
fun LikeVideoTab(
    viewModel: CreatorProfileViewModel,
) {
    val state by viewModel.viewState.collectAsState()

    state?.creatorProfile?.apply {
        if (isLikedVideoPrivate) {
            PrivateLikedVideoLayout(userName = this.uniqueUserName)
        } else {
            //list liked video
        }
    }
}

@Composable
internal fun PrivateLikedVideoLayout(userName: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        30.dp.Space()
        Text(
            text = stringResource(id = R.string.this_users_liked_videos),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = " ${stringResource(id = R.string.videos_liked_by)} $userName ${
                stringResource(
                    id = R.string.currently_hidden
                )
            }",
            textAlign = TextAlign.Center,
            color = Gray,
            modifier = Modifier.fillMaxWidth(0.9f)
        )

    }

}