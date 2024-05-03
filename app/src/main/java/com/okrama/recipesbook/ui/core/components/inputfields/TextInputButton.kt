package com.okrama.recipesbook.ui.core.components.inputfields

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun TextInputIconButton(
    painter: Painter,
    onClick: () -> Unit,
    color: Color = LocalContentColor.current,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painter,
            tint = color,
            contentDescription = contentDescription
        )
    }
}