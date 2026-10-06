package com.yachiqo.spotgo.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val SpotGoColors = darkColorScheme(
    primary = SpotGoAccent,
    onPrimary = SpotGoOnAccent,
    primaryContainer = SpotGoAccentContainer,
    onPrimaryContainer = SpotGoAccent,
    secondary = SpotGoAccent,
    onSecondary = SpotGoOnAccent,
    secondaryContainer = SpotGoAccentContainer,
    onSecondaryContainer = SpotGoAccent,
    tertiary = SpotGoSupportingText,
    background = SpotGoBackground,
    onBackground = SpotGoText,
    surface = SpotGoBackground,
    onSurface = SpotGoText,
    surfaceVariant = SpotGoSurface,
    onSurfaceVariant = SpotGoSecondaryText,
    surfaceContainer = SpotGoSurface,
    surfaceContainerLow = SpotGoSurface,
    surfaceContainerHigh = SpotGoSurface,
    outline = SpotGoOutline,
    outlineVariant = SpotGoOutline,
)

// Keep the Figma palette even when the device uses light mode or dynamic colors.
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SpotGoTheme(content: @Composable () -> Unit) {
    MaterialExpressiveTheme(
        colorScheme = SpotGoColors,
        typography = Typography,
        shapes = Shapes(
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp),
        ),
        content = content,
    )
}
