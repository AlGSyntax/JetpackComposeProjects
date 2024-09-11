package com.algsyntax.todojetpackcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Define the dark color scheme (black background, white text)
private val DarkColorScheme = darkColorScheme(
    primary = Color.White,  // White text
    secondary = Color.Gray,
    tertiary = Color.DarkGray,
    background = Color.Black,  // Black background
    surface = Color.Black,  // Also black for surfaces
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,  // White text on black background
    onSurface = Color.White  // White text on black surfaces
)

// Define the light color scheme (white background, black text)
private val LightColorScheme = lightColorScheme(
    primary = Color.Black,  // Black text
    secondary = Color.Gray,
    tertiary = Color.DarkGray,
    background = Color.White,  // White background
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,  // Black text on white background
    onSurface = Color.Black  // Black text on white surfaces
)

@Composable
fun ToDoJetpackComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,  // Disable dynamic color for manual control
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme  // Use the dark theme with black background
    } else {
        LightColorScheme  // Use the light theme with white background
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}