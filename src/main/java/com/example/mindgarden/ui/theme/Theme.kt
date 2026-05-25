package com.example.mindgarden.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val GreenLight = Color(0xFF3B6D11)
private val GreenContainer = Color(0xFFEAF3DE)
private val GreenOnContainer = Color(0xFF173404)

private val LightColorScheme = lightColorScheme(
    primary = GreenLight,
    onPrimary = Color.White,
    primaryContainer = GreenContainer,
    onPrimaryContainer = GreenOnContainer,
    secondary = Color(0xFF639922),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEAF3DE),
    onSecondaryContainer = Color(0xFF27500A),
    tertiary = Color(0xFF4A7C3F),
    background = Color(0xFFFCFCF8),
    surface = Color(0xFFFCFCF8),
    surfaceVariant = Color(0xFFF1EFE8),
    onSurfaceVariant = Color(0xFF5F5E5A),
    outline = Color(0xFF8F8D84),
    outlineVariant = Color(0xFFD3D1C7),
    error = Color(0xFFA32D2D),
    onError = Color.White,
    errorContainer = Color(0xFFFCEBEB),
    onErrorContainer = Color(0xFF501313)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF97C459),
    onPrimary = Color(0xFF173404),
    primaryContainer = Color(0xFF27500A),
    onPrimaryContainer = Color(0xFFC0DD97),
    secondary = Color(0xFF97C459),
    onSecondary = Color(0xFF173404),
    secondaryContainer = Color(0xFF3B6D11),
    onSecondaryContainer = Color(0xFFEAF3DE),
    tertiary = Color(0xFF5DCAA5),
    background = Color(0xFF1A1C18),
    surface = Color(0xFF1A1C18),
    surfaceVariant = Color(0xFF2C2E28),
    onSurfaceVariant = Color(0xFFB4B2A9),
    outline = Color(0xFF5F5E5A),
    outlineVariant = Color(0xFF3C3D38),
    error = Color(0xFFF09595),
    onError = Color(0xFF501313),
    errorContainer = Color(0xFF791F1F),
    onErrorContainer = Color(0xFFF7C1C1)
)

@Composable
fun MindGardenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
