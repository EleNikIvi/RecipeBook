package com.okrama.recipesbook.ui.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.okrama.recipesbook.ui.core.components.inputfields.defaultTextSelectionColors


@Composable
fun RecipesBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Green0.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val selectionColors = defaultTextSelectionColors()
    MaterialTheme {
        // Replace Typography system with a custom implementation
        CompositionLocalProvider(
            LocalRecipesBookTypography provides Typography,
            LocalTextSelectionColors provides selectionColors,
            content = content,
        )
    }
}

object RecipesBookTheme {
    val typography: RecipesBookTypography
        @Composable
        get() = LocalRecipesBookTypography.current

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