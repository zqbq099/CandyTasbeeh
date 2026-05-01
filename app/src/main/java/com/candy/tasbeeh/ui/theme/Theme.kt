package com.candy.tasbeeh.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun CandyTasbeehTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = darkColorScheme(
        primary = GemGold,
        onPrimary = Background,
        primaryContainer = GemGold.copy(alpha = 0.7f),
        secondary = GemGold,
        background = Background,
        surface = Surface,
        onSurface = TextPrimary,
        onBackground = TextPrimary,
        error = GemRed,
        onError = Color.White
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as androidx.activity.ComponentActivity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            window.statusBarColor = Background.toArgb()
            window.navigationBarColor = Background.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
