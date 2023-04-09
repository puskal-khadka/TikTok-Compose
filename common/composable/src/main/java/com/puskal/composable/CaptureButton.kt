package com.puskal.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Puskal Khadka on 4/2/2023.
 */

@Preview
@Composable
fun CaptureButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    borderColor: Color = Color.White,
    size: Dp = 82.dp,
    onClickCapture: () -> Unit = {}
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(size)
                .border(width = 5.dp, color = borderColor, shape = CircleShape)
                .padding(8.dp)
                .background(color, CircleShape)
                .clickable {
                    onClickCapture()
                }
        )
    )
}