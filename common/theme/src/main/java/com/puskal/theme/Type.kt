package com.puskal.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),

    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 27.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Bold
    ),

    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 17.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),

    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold
    ),

    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold

    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold
    )

)



