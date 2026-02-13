package com.example.agrinyay.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AgriGreen = Color(0xFF2E7D32)
private val AgriAccent = Color(0xFF81C784)
private val AgriLightBg = Color(0xFFF1F8E9)

private val LightColorScheme = lightColorScheme(
    primary = AgriGreen,
    onPrimary = Color.White,
    secondary = AgriAccent,
    background = AgriLightBg,
    surface = Color.White,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = AgriAccent,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

@Composable
fun AgriNyayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
