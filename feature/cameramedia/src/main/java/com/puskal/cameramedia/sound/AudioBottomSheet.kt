package com.puskal.cameramedia.sound

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.*
import com.puskal.composable.TiktokBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.compose.animation.core.*
import coil.compose.AsyncImage
import com.puskal.data.model.AudioModel
import com.puskal.theme.GrayMainColor
import com.puskal.theme.Gray
import com.puskal.theme.PrimaryColor
import com.puskal.theme.R
import com.puskal.theme.SeparatorColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioBottomSheet(
    onDismiss: () -> Unit,
    viewModel: ChooseSoundViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    var search by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val shuffledLists = remember(viewState?.audioFiles) {
        val list = viewState?.audioFiles ?: emptyList()
        List(4) { list.shuffled() }
    }

    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var playingItem by remember { mutableStateOf<AudioModel?>(null) }
    var isMuted by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    TiktokBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 4.dp)
                        .background(color = Gray, shape = RoundedCornerShape(2.dp))
                )
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(2.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(horizontal = 20.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_singer_music_lyric_emotion))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Gray,
                    unfocusedTextColor = Gray,
                    focusedPlaceholderColor = Gray,
                    unfocusedPlaceholderColor = Gray,
                    focusedLeadingIconColor = Gray,
                    unfocusedLeadingIconColor = Gray,
                    focusedContainerColor = GrayMainColor,
                    unfocusedContainerColor = GrayMainColor
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val tabs = listOf(
                    stringResource(id = R.string.recommended),
                    stringResource(id = R.string.hot),
                    stringResource(id = R.string.favorites),
                    stringResource(id = R.string.used)
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.weight(1f),
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    color = Color.Black,
                                    fontSize = 10.sp
                                )
                            }
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp, start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AudioFile,
                        contentDescription = null,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.import_audio),
                        color = Color.Black,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentPadding = PaddingValues(bottom = 56.dp)
            ) {
                val list = shuffledLists.getOrNull(selectedTab) ?: emptyList()
                items(list) { audio ->
                    val isPlaying = playingItem == audio
                    AudioRow(
                        audio = audio,
                        isPlaying = isPlaying,
                        onClick = {
                            if (isPlaying) {
                                exoPlayer.stop()
                                playingItem = null
                            } else {
                                exoPlayer.setMediaItem(
                                    MediaItem.fromUri("asset:///audios/${audio.originalVideoUrl}")
                                )
                                exoPlayer.prepare()
                                exoPlayer.playWhenReady = true
                                playingItem = audio
                            }
                        }
                    )
                    Divider(thickness = 0.5.dp, color = SeparatorColor)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomAction(
                    text = stringResource(id = R.string.original_sound),
                    icon = ImageVector.vectorResource(id = R.drawable.ic_music_note)
                ) {
                    exoPlayer.stop()
                    playingItem = null
                }
                BottomAction(
                    text = stringResource(id = R.string.music_off),
                    icon = ImageVector.vectorResource(id = R.drawable.ic_music_off)
                ) {
                    isMuted = !isMuted
                }
                BottomAction(
                    text = stringResource(id = R.string.volume),
                    icon = Icons.Outlined.Tune
                ) {
                    // TODO: Show volume controls
                }
                BottomAction(
                    text = stringResource(id = R.string.subtitle),
                    icon = Icons.Outlined.Subtitles
                ) {
                    // TODO: Open subtitles options
                }
            }
        }
    }
}

@Composable
private fun BottomAction(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = Color.Black)
    }
}

@Composable
private fun PlayingAnimation(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition()

    val heights = listOf(0, 1, 2).map { index ->
        infinite.animateFloat(
            initialValue = 4f,
            targetValue = 12f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 600
                    12f at 300
                    4f at 600
                },
                initialStartOffset = StartOffset(index * 100)
            )
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        heights.forEach { h ->
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(h.value.dp)
                    .background(PrimaryColor, RoundedCornerShape(1.dp))
            )
        }
    }
}

@Composable
private fun AudioRow(
    audio: AudioModel,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = audio.parseCoverImage(),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .border(
                    BorderStroke(width = 1.dp, color = if (isPlaying) PrimaryColor else Color.Transparent),
                    shape = RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "${audio.audioAuthor.fullName} \u2022 ${audio.duration}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isPlaying) PrimaryColor else Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )

        if (isPlaying) {
            PlayingAnimation(modifier = Modifier.padding(end = 8.dp))
        }

        if (isPlaying) {
            Row {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.ContentCut,
                        contentDescription = stringResource(id = R.string.cut),
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}
