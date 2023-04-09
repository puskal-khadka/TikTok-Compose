package com.puskal.core.extension

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Puskal Khadka on 3/15/2023.
 */


@Composable
fun Dp.Space() = Spacer(
    modifier = Modifier
        .height(this)
)

@Composable
fun SmallSpace() = Spacer(modifier = Modifier.height(16.dp))

@Composable
fun MediumSpace() = Spacer(modifier = Modifier.height(26.dp))

@Composable
fun LargeSpace() = Spacer(modifier = Modifier.height(32.dp))