package com.puskal.cameramedia.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.puskal.theme.R
import com.redevrx.video_trimmer.utils.TrimVideoUtils

@Composable
fun VideoOperationBar(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    currentPosition: Long,
    totalDuration: Long,
    onPlayPause: () -> Unit,
    onUndo: () -> Unit = {},
    onRedo: () -> Unit = {},
    onFullScreen: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${TrimVideoUtils.stringForTime(currentPosition)} / ${TrimVideoUtils.stringForTime(totalDuration)}",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )

        IconButton(onClick = onPlayPause) {
            if (isPlaying) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = stringResource(id = R.string.pause),
                    tint = Color.White
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = stringResource(id = R.string.play),
                    tint = Color.White
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onUndo) {
                Icon(
                    imageVector = Icons.Filled.Undo,
                    contentDescription = stringResource(id = R.string.undo),
                    tint = Color.White
                )
            }
            IconButton(onClick = onRedo) {
                Icon(
                    imageVector = Icons.Filled.Redo,
                    contentDescription = stringResource(id = R.string.redo),
                    tint = Color.White
                )
            }
            IconButton(onClick = onFullScreen) {
                Icon(
                    imageVector = Icons.Filled.Fullscreen,
                    contentDescription = stringResource(id = R.string.fullscreen),
                    tint = Color.White
                )
            }
        }
    }
}
