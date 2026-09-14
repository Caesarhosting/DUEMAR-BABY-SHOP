package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Color(0xFF38BDF8),
    onPrimary = Color(0xFF00354E),
    primaryContainer = Color(0xFF004D71),
    onPrimaryContainer = Color(0xFFC2E8FF),
    secondary = Color(0xFFF472B6),
    onSecondary = Color(0xFF501A33),
    secondaryContainer = Color(0xFF702847),
    onSecondaryContainer = Color(0xFFFFD9E6),
    tertiary = Color(0xFF2DD4BF),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = BabyBluePrimary,
    onPrimary = Color.White,
    primaryContainer = BabyBlueLight,
    onPrimaryContainer = BabyBlueDark,
    secondary = BabyPeachSecondary,
    onSecondary = Color.White,
    secondaryContainer = BabyPeachLight,
    onSecondaryContainer = BabyPeachDark,
    tertiary = BabyMintTertiary,
    tertiaryContainer = BabyMintLight,
    background = NeutralBackground,
    surface = NeutralSurface,
    surfaceVariant = NeutralSurfaceVariant,
    onBackground = NeutralTextPrimary,
    onSurface = NeutralTextPrimary,
    onSurfaceVariant = NeutralTextSecondary,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Use our brand pastel palette for Duemar Baby Shop
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
