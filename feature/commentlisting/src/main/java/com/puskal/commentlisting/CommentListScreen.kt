package com.puskal.commentlisting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.puskal.core.extension.Space
import com.puskal.data.model.CommentList
import com.puskal.theme.DarkBlue
import com.puskal.theme.GrayMainColor
import com.puskal.theme.R
import com.puskal.theme.SubTextColor


/**
 * Created by Puskal Khadka on 3/22/2023.
 */


@Composable
fun CommentListScreen(
    viewModel: CommentListViewModel = hiltViewModel(),
    onClickCancel: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight(0.75f)
    ) {
        12.dp.Space()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "${viewState?.comments?.totalComment ?: ""} ${stringResource(id = R.string.comments)}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onClickCancel()
                    }
            )
        }

        6.dp.Space()
        LazyColumn(contentPadding = PaddingValues(top = 4.dp), modifier = Modifier.weight(1f)) {
            viewState?.comments?.comments?.let {
                items(it) {
                    CommentItem(it)
                }
            }
        }




        CommentUserField()
    }
}


@Composable
fun CommentItem(item: CommentList.Comment) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (profileImg, name, comment, createdOn, reply, like, dislike) = createRefs()

        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(item.commentBy.profilePic)
            .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GrayMainColor)
                .constrainAs(profileImg) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                })


        Text(text = item.commentBy.fullName,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(profileImg.end, margin = 12.dp)
                top.linkTo(profileImg.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(text = item.comment ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = DarkBlue,
            modifier = Modifier.constrainAs(comment) {
                start.linkTo(name.start)
                top.linkTo(name.bottom, margin = 5.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(text = item.createdAt, modifier = Modifier.constrainAs(createdOn) {
            start.linkTo(name.start)
            top.linkTo(comment.bottom, margin = 5.dp)
        })

        Text(text = stringResource(id = R.string.reply),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(reply) {
                start.linkTo(createdOn.end, margin = 16.dp)
                top.linkTo(createdOn.top)
                end.linkTo(like.end, margin = 4.dp)
                width = Dimension.fillToConstraints
            })

        Row(
            modifier = Modifier.constrainAs(like) {
                bottom.linkTo(reply.bottom)
                end.linkTo(dislike.start, margin = 24.dp)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            item.totalLike.takeIf { it != 0L }?.let {
                Text(text = it.toString(), fontSize = 13.sp, color = SubTextColor)
            }

        }

        Row(
            modifier = Modifier.constrainAs(dislike) {
                bottom.linkTo(reply.bottom)
                end.linkTo(parent.end)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dislike_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            // Text(text = "") //dislike not display
        }
    }
    24.dp.Space()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentUserField() {
    Column(
        modifier = Modifier
            .shadow(elevation = (0.4).dp)
            .padding(horizontal = 16.dp)
    ) {
        HighlightedEmoji.values().toList().let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                it.forEach { emoji ->
                    Text(text = emoji.unicode, fontSize = 25.sp)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "", contentDescription = null, modifier = Modifier
                    .size(38.dp)
                    .background(
                        shape =
                        CircleShape, color = GrayMainColor
                    )
            )

            OutlinedTextField(value = "",
                onValueChange = {},
                shape = RoundedCornerShape(36.dp),
                placeholder = {
                    Text(text = stringResource(R.string.add_comment))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = GrayMainColor,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier.height(46.dp),
                enabled=false,
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(end = 10.dp, start = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mention),
                            contentDescription = null
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_emoji),
                            contentDescription = null
                        )
                    }

                }

            )
        }
    }
}