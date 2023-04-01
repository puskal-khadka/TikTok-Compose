package com.puskal.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Puskal Khadka on 3/24/2023.
 */

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    shape: Shape = RoundedCornerShape(2.dp),
    height: Dp = 44.dp,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    isEnabled: Boolean = true,
    onClickButton: () -> Unit
) {
    Button(
        onClick = { onClickButton.invoke() },
        modifier = modifier.height(height),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        enabled = isEnabled
    ) {
        Text(text = buttonText, style = style)
    }
}