package com.puskal.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A convenience wrapper around [CustomButton] that uses a large rounded shape.
 */
@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClickButton: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        buttonText = buttonText,
        shape = RoundedCornerShape(percent = 50),
        style = MaterialTheme.typography.labelLarge,
        containerColor = containerColor,
        onClickButton = onClickButton
    )
}
