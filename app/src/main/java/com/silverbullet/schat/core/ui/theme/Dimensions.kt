package com.silverbullet.schat.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Dimensions(
    val default: Dp = 0.dp,
    val extraSmallSpace: Dp = 4.dp,
    val smallSpace: Dp = 8.dp,
    val mediumSpace: Dp = 16.dp,
    val largeSpace: Dp =24.dp,
    val extraLargeSpace: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }