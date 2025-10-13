package com.puskal.cameramedia

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.puskal.theme.R

/**
 * Simple bar showing an icon and text for adding music.
 */
@Composable
fun MusicBarLayout(
    modifier: Modifier = Modifier,
    onClickAddSound: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClickAddSound() },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_music_note),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = stringResource(id = R.string.add_sound),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

