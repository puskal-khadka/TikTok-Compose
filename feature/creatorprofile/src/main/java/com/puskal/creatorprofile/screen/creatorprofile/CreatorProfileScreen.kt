package com.puskal.creatorprofile.screen.creatorprofile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.puskal.composable.TopBar
import com.puskal.core.AppContract.Type.INSTAGRAM
import com.puskal.core.AppContract.Type.YOUTUBE
import com.puskal.core.DestinationRoute.CREATOR_VIDEO_ROUTE
import com.puskal.core.utils.IntentUtils.redirectToApp
import com.puskal.creatorprofile.component.*
import com.puskal.data.model.SocialMediaType
import com.puskal.data.model.UserModel
import com.puskal.theme.*
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 3/22/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorProfileScreen(
    onClickNavIcon: () -> Unit,
    navController: NavController,
    viewModel: CreatorProfileViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBar(
                onClickNavIcon = onClickNavIcon,
                title = viewState?.creatorProfile?.fullName ?: "",
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { })
                })
        }
    ) {
        BoxWithConstraints {
            val height = this.maxHeight
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileDetails(viewState?.creatorProfile)
                VideoListingPager(scrollState = scrollState, height = height, viewModel,
                    onClickVideo = { video, index ->
                        navController.navigate("$CREATOR_VIDEO_ROUTE/${viewModel.userId}/$index")
                    }
                )
            }
        }

    }
}

@Composable
fun ColumnScope.ProfileDetails(creatorProfile: UserModel?) {
    val context = LocalContext.current
    AsyncImage(
        model = creatorProfile?.profilePic,
        contentDescription = null,
        modifier = Modifier
            .size(94.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "@${creatorProfile?.uniqueUserName}",
            style = MaterialTheme.typography.bodyMedium
        )
        if (creatorProfile?.isVerified == true) {
            Icon(
                painter = painterResource(id = R.drawable.ic_verified),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

    }
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (
            followingCount, tvFollowing, separator1,
            followersCount, tvFollowers, separator2, likeCount, tvLike,
        ) = createRefs()


        Text(
            text = creatorProfile?.formattedFollowingCount ?: "-",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.constrainAs(followingCount) {
                top.linkTo(parent.top)
                centerHorizontallyTo(tvFollowing)
            }
        )
        Text(
            text = stringResource(id = R.string.following),
            color = SubTextColor,
            modifier = Modifier.constrainAs(tvFollowing) {
                top.linkTo(followingCount.bottom, margin = 3.dp)
                start.linkTo(parent.start, margin = 36.dp)
                end.linkTo(separator1.start)
            }
        )

        Divider(modifier = Modifier
            .width(1.dp)
            .height(16.dp)
            .constrainAs(separator1) {
                start.linkTo(tvFollowing.end)
                end.linkTo(tvFollowers.start)
                bottom.linkTo(tvFollowing.bottom)
                top.linkTo(followingCount.top)
            })


        Text(
            text = creatorProfile?.formattedFollowersCount ?: "-",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.constrainAs(followersCount) {
                top.linkTo(followingCount.top)
                centerHorizontallyTo(tvFollowers)
            }
        )

        Text(
            text = stringResource(id = R.string.followers),
            color = SubTextColor,
            modifier = Modifier.constrainAs(tvFollowers) {
                start.linkTo(separator1.end)
                end.linkTo(separator2.start)
                top.linkTo(followersCount.bottom, margin = 3.dp)
            }
        )


        Divider(modifier = Modifier
            .width(1.dp)
            .height(16.dp)
            .constrainAs(separator2) {
                start.linkTo(tvFollowers.end)
                end.linkTo(tvLike.start)
                top.linkTo(followingCount.top)
                bottom.linkTo(tvFollowers.bottom)
            })

        Text(
            text = creatorProfile?.formattedLikeCount ?: "-",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.constrainAs(likeCount) {
                top.linkTo(followingCount.top)
                centerHorizontallyTo(tvLike)
            }
        )

        Text(
            text = stringResource(id = R.string.likes),
            color = SubTextColor,
            modifier = Modifier.constrainAs(tvLike) {
                top.linkTo(likeCount.bottom, margin = 3.dp)
                start.linkTo(separator2.end)
                end.linkTo(parent.end, margin = 36.dp)
            }
        )
    }


    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { }, modifier = Modifier
                .width(158.dp)
                .height(42.dp), shape = RoundedCornerShape(2.dp)
        ) {
            Text(text = stringResource(id = R.string.follow))
        }
        creatorProfile?.pinSocialMedia?.let {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(2.dp),
                        color = Gray.copy(alpha = 0.2f)
                    )
                    .clickable {
                        context.redirectToApp(
                            link = it.link,
                            type = when (it.type) {
                                SocialMediaType.INSTAGRAM -> INSTAGRAM
                                SocialMediaType.YOUTUBE -> YOUTUBE
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                val icon = when (it.type) {
                    SocialMediaType.INSTAGRAM -> R.drawable.ic_instagram
                    SocialMediaType.YOUTUBE -> R.drawable.ic_youtube
                }
                Icon(painter = painterResource(id = icon), contentDescription = null)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .border(
                    width = 1.dp, shape = RoundedCornerShape(2.dp), color = Gray.copy(alpha = 0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_down_more), contentDescription = null)
        }
    }

    Text(
        text = creatorProfile?.bio ?: stringResource(id = R.string.no_bio_yet),
    )


}




