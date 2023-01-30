package com.midcores.ahlanarabiyyah.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing (
    val default: Dp = 0.dp,
    val line: Dp = 1.dp,
    val veryTiny: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val lessMedium: Dp = 10.dp,
    val medium: Dp = 16.dp,
    val lessLarge: Dp = 24.dp,
    val large: Dp = 32.dp,
    val quiteLarge: Dp = 36.dp,
    val veryLarge: Dp = 48.dp,
    val extraLarge: Dp = 56.dp,
    val superLarge: Dp = 64.dp,
    val lessHuge: Dp = 72.dp,
    val huge: Dp = 90.dp,
    val veryHuge: Dp = 110.dp,
    val extraHuge: Dp = 150.dp,
    val doubledHuge: Dp = 180.dp,
    val superHuge: Dp = 256.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current