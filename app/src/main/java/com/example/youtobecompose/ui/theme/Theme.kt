package com.example.youtobecompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        primary = YoutubeRed,
        onPrimary = YoutubeOnRed,
        primaryContainer = YoutubeDarkRed,
        onPrimaryContainer = YoutubeOnRed,
        secondary = YoutubeDarkOnSurfaceVariant,
        onSecondary = YoutubeDarkBackground,
        secondaryContainer = YoutubeDarkSurfaceVariant,
        onSecondaryContainer = YoutubeDarkOnSurface,
        tertiary = YoutubeAvatarAqua,
        onTertiary = YoutubeDarkBackground,
        background = YoutubeDarkBackground,
        onBackground = YoutubeDarkOnSurface,
        surface = YoutubeDarkSurface,
        onSurface = YoutubeDarkOnSurface,
        surfaceVariant = YoutubeDarkSurfaceVariant,
        onSurfaceVariant = YoutubeDarkOnSurfaceVariant,
        outline = YoutubeDarkDivider,
        outlineVariant = YoutubeDarkDivider,
        error = YoutubeRed,
        onError = YoutubeOnRed,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = YoutubeRed,
        onPrimary = YoutubeOnRed,
        primaryContainer = YoutubeRedContainer,
        onPrimaryContainer = YoutubeOnRedContainer,
        secondary = YoutubeTextSecondary,
        onSecondary = YoutubeSurface,
        secondaryContainer = YoutubeSurfaceVariant,
        onSecondaryContainer = YoutubeOnSurface,
        tertiary = YoutubeAvatarAqua,
        onTertiary = YoutubeOnSurface,
        background = YoutubeBackground,
        onBackground = YoutubeOnSurface,
        surface = YoutubeSurface,
        onSurface = YoutubeOnSurface,
        surfaceVariant = YoutubeSurfaceVariant,
        onSurfaceVariant = YoutubeOnSurfaceVariant,
        outline = YoutubeChipBorder,
        outlineVariant = YoutubeDivider,
        error = YoutubeRed,
        onError = YoutubeOnRed,
    )

@Composable
fun YoutobeComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
