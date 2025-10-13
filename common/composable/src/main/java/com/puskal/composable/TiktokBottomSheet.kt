package com.puskal.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.puskal.theme.Black
import com.puskal.theme.Gray
import com.puskal.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiktokBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = White,
    contentColor: Color = Black,
    dragHandleColor: Color = Gray,
    dragHandle: @Composable (() -> Unit)? = { DefaultDragHandle(color = dragHandleColor) },
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        dragHandle = dragHandle,
        content = content
    )
}

@Composable
private fun DefaultDragHandle(color: Color) {
    Box(
        modifier = Modifier
            .padding(top = 6.dp)
            .size(width = 32.dp, height = 4.dp)
            .background(color = color, shape = RoundedCornerShape(2.dp))
    )
}
