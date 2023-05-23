package com.doanda.jetsteam.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
    primary = SuperDarkBlue,
    primaryVariant = DarkDullBlue,
    secondary = DarkBlue,
    secondaryVariant = Blue,
//    /* Other default colors to override
    background = DarkDullBlue,
    surface = DarkBlue,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
//    */
)

private val LightColorPalette = lightColors(
    primary = SuperDarkBlue,
    primaryVariant = DarkDullBlue,
    secondary = DarkBlue,
    secondaryVariant = Blue,

//    /* Other default colors to override
    background = DarkDullBlue,
    surface = DarkBlue,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
//    */
)

@Composable
fun JetSteamTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}