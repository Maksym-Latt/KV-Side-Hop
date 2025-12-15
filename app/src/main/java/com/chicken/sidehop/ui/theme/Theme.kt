package com.chicken.sidehop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = YellowDeep,
    secondary = RedPrimary,
    tertiary = GrassGreen,
    background = SkyBlue,
    surface = CreamPanel,
    onPrimary = OutlineDark,
    onSecondary = OutlineDark
)

@Composable
fun ChickenSideHopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}