package com.puskal.composable


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.puskal.core.extension.Space
import com.puskal.core.utils.IntentUtils.share
import com.puskal.data.model.VideoModel
import com.puskal.theme.*
import com.puskal.theme.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Puskal Khadka on 3/16/2023.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun TikTokVerticalVideoPager(
    modifier: Modifier = Modifier,
    videos: List<VideoModel>,
    initialPage: Int? = 0,
    showUploadDate: Boolean = false,
    onclickComment: (videoId: String) -> Unit,
    onClickLike: (videoId: String, likeStatus: Boolean) -> Unit,
    onclickFavourite: (videoId: String) -> Unit,
    onClickAudio: (VideoModel) -> Unit,
    onClickUser: (userId: Long) -> Unit,
    onClickFavourite: (isFav: Boolean) -> Unit = {},
    onClickShare: (() -> Unit)? = null
) {
    val pagerState = rememberPagerState(initialPage = initialPage ?: 0)
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val fling = PagerDefaults.flingBehavior(
        state = pagerState, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )

    VerticalPager(
        pageCount = videos.size,
        state = pagerState,
        flingBehavior = fling,
        beyondBoundsPageCount = 1,
        modifier = modifier
    ) {
        var pauseButtonVisibility by remember { mutableStateOf(false) }
        var doubleTapState by remember {
            mutableStateOf(
                Triple(
                    Offset.Unspecified, //offset
                    false, //double tap anim start
                    0f //rotation angle
                )
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            VideoPlayer(videos[it], pagerState, it, onSingleTap = {
                pauseButtonVisibility = it.isPlaying
                it.playWhenReady = !it.isPlaying
            },
                onDoubleTap = { exoPlayer, offset ->
                    coroutineScope.launch {
                        videos[it].currentViewerInteraction.isLikedByYou = true
                        val rotationAngle = (-10..10).random()
                        doubleTapState = Triple(offset, true, rotationAngle.toFloat())
                        delay(400)
                        doubleTapState = Triple(offset, false, rotationAngle.toFloat())
                    }
                },
                onVideoDispose = { pauseButtonVisibility = false },
                onVideoGoBackground = { pauseButtonVisibility = false }

            )


            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    FooterUi(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        item = videos[it],
                        showUploadDate=showUploadDate,
                        onClickAudio = onClickAudio,
                        onClickUser = onClickUser,
                    )

                    SideItems(
                        modifier = Modifier,
                        videos[it],
                        doubleTabState = doubleTapState,
                        onclickComment = onclickComment,
                        onClickUser = onClickUser,
                        onClickFavourite = onClickFavourite,
                        onClickShare = onClickShare
                    )
                }
                12.dp.Space()
            }


            AnimatedVisibility(
                visible = pauseButtonVisibility,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                exit = scaleOut(tween(150)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
            }

            val iconSize = 110.dp
            AnimatedVisibility(visible = doubleTapState.second,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.3f),
                exit = scaleOut(
                    tween(600), targetScale = 1.58f
                ) + fadeOut(tween(600)) + slideOutVertically(
                    tween(600)
                ),
                modifier = Modifier.run {
                    if (doubleTapState.first != Offset.Unspecified) {
                        this.offset(x = localDensity.run {
                            doubleTapState.first.x.toInt().toDp().plus(-iconSize.div(2))
                        }, y = localDensity.run {
                            doubleTapState.first.y.toInt().toDp().plus(-iconSize.div(2))
                        })
                    } else this
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_like),
                    contentDescription = null,
                    tint = if (doubleTapState.second) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.8f
                    ),
                    modifier = Modifier
                        .size(iconSize)
                        .rotate(doubleTapState.third)
                )
            }


        }
    }

}


@Composable
fun SideItems(
    modifier: Modifier,
    item: VideoModel,
    doubleTabState: Triple<Offset, Boolean, Float>,
    onclickComment: (videoId: String) -> Unit,
    onClickUser: (userId: Long) -> Unit,
    onClickShare: (() -> Unit)? = null,
    onClickFavourite: (isFav: Boolean) -> Unit
) {

    val context = LocalContext.current
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = item.authorDetails.profilePic,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .border(
                    BorderStroke(width = 1.dp, color = White), shape = CircleShape
                )
                .clip(shape = CircleShape)
                .clickable {
                    onClickUser.invoke(item.authorDetails.userId)
                },
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(5.5.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )

        12.dp.Space()

        var isLiked by remember {
            mutableStateOf(item.currentViewerInteraction.isLikedByYou)
        }

        LaunchedEffect(key1 = doubleTabState) {
            if (doubleTabState.first != Offset.Unspecified && doubleTabState.second) {
                isLiked = doubleTabState.second
            }
        }
        LikeIconButton(isLiked = isLiked,
            likeCount = item.videoStats.formattedLikeCount,
            onLikedClicked = {
                isLiked = it
                item.currentViewerInteraction.isLikedByYou = it
            })


        Icon(painter = painterResource(id = R.drawable.ic_comment),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(33.dp)
                .clickable {
                    onclickComment(item.videoId)
                })
        Text(
            text = item.videoStats.formattedCommentCount,
            style = MaterialTheme.typography.labelMedium
        )
        16.dp.Space()



        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(33.dp)
        )
        Text(
            text = item.videoStats.formattedFavouriteCount,
            style = MaterialTheme.typography.labelMedium
        )
        14.dp.Space()

        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onClickShare?.let { onClickShare.invoke() } ?: run {
                        context.share(
                            text = "https://github.com/puskal-khadka"
                        )
                    }
                }
        )
        Text(
            text = item.videoStats.formattedShareCount, style = MaterialTheme.typography.labelMedium
        )
        20.dp.Space()

        RotatingAudioView(item.authorDetails.profilePic)

    }
}

@Composable
fun LikeIconButton(
    isLiked: Boolean, likeCount: String, onLikedClicked: (Boolean) -> Unit
) {

    val maxSize = 38.dp
    val iconSize by animateDpAsState(targetValue = if (isLiked) 33.dp else 32.dp,
        animationSpec = keyframes {
            durationMillis = 400
            24.dp.at(50)
            maxSize.at(190)
            26.dp.at(330)
            32.dp.at(400).with(FastOutLinearInEasing)
        })

    Box(
        modifier = Modifier
            .size(maxSize)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                onLikedClicked(!isLiked)
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.White,
            modifier = Modifier.size(iconSize)
        )
    }

    Text(text = likeCount, style = MaterialTheme.typography.labelMedium)
    16.dp.Space()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FooterUi(
    modifier: Modifier,
    item: VideoModel,
    showUploadDate: Boolean,
    onClickAudio: (VideoModel) -> Unit,
    onClickUser: (userId: Long) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            onClickUser(item.authorDetails.userId)
        }) {
            Text(
                text = item.authorDetails.fullName, style = MaterialTheme.typography.bodyMedium
            )
            if (showUploadDate) {
                Text(
                    text = " . ${item.createdAt} ago",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
        5.dp.Space()
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        10.dp.Space()
        val audioInfo: String = item.audioModel?.run {
            "Original sound - ${audioAuthor.uniqueUserName} - ${audioAuthor.fullName}"
        }
            ?: item.run { "Original sound - ${item.authorDetails.uniqueUserName} - ${item.authorDetails.fullName}" }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                onClickAudio(item)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = audioInfo,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .basicMarquee()
            )
        }
    }
}


@Composable
fun RotatingAudioView(img: String) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = keyframes { durationMillis = 7000 })
    )

    Box(modifier = Modifier.rotate(angle)) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Gray20, Gray20, GrayLight, Gray20, Gray20,
                        )
                    ), shape = CircleShape
                )
                .size(46.dp), contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = img,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        }
    }

}


