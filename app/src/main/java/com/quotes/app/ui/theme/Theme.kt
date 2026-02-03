package com.quotes.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = TextBlack,
    primaryContainer = SkyBlueLight,
    onPrimaryContainer = TextBlack,
    secondary = SkyBlueDark,
    onSecondary = BackgroundWhite,
    secondaryContainer = CardGrey,
    onSecondaryContainer = TextBlack,
    tertiary = AccentGreen,
    onTertiary = BackgroundWhite,
    error = AccentRed,
    onError = BackgroundWhite,
    background = BackgroundWhite,
    onBackground = TextBlack,
    surface = CardGrey,
    onSurface = TextBlack,
    surfaceVariant = CardGreyDark,
    onSurfaceVariant = TextGrey,
    outline = TextLightGrey
)

@Composable
fun QuotesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Only use light theme as per requirements
    val colorScheme = LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
