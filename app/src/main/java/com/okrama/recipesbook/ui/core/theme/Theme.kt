package com.okrama.recipesbook.ui.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = Green0,
    secondary = Grey0,
    tertiary = Grey2,
)

@Composable
fun RecipesBookTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Green0.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
    )
}

object RecipesBookTheme {

    val elevation = Elevation()
    val shapes = Shapes()
    data class Elevation internal constructor(
        val small: Dp = 8.dp,
        val medium: Dp = 12.dp
    )

    data class Shapes internal constructor(
        val small: CornerBasedShape = RoundedCornerShape(4.dp),
        val medium: CornerBasedShape = RoundedCornerShape(8.dp),
        val large: CornerBasedShape = RoundedCornerShape(12.dp),
        val xLarge: CornerBasedShape = RoundedCornerShape(16.dp),
        val xxLarge: CornerBasedShape = RoundedCornerShape(20.dp),
        val dialog: CornerBasedShape = RoundedCornerShape(28.dp)
    )
}