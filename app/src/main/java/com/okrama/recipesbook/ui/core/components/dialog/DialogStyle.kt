package com.okrama.recipesbook.ui.core.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.backgroundLight

object DialogStyle {
    val shape: Shape
        @Composable
        @ReadOnlyComposable
        get() = RecipesBookTheme.shapes.dialog

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = backgroundLight

    val contentPadding: Dp
        get() = 24.dp

    val buttonPadding: Dp
        get() = 24.dp

    val capitalizeButton: Boolean
        get() = false
}