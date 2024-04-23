package com.okrama.recipesbook.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.ui.core.theme.Green1
import com.okrama.recipesbook.ui.core.theme.Grey0
import com.okrama.recipesbook.ui.core.theme.Grey5
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RecipesBookTheme.shapes.medium,
    backgroundColor: Color = Grey0,
    contentColor: Color = Green1,
    border: BorderStroke? = null,
    elevation: Dp = RecipesBookTheme.elevation.small,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enforceTouchTargetSize: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides enforceTouchTargetSize
    ) {
        Card(
            onClick = onClick,
            modifier = modifier.shadow(
                elevation = elevation,
                shape = shape,
                spotColor = Grey5
            ),
            enabled = enabled,
            shape = shape,
            colors = CardDefaults.cardColors(contentColor = contentColor, containerColor = backgroundColor,),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            border = border,
            interactionSource = interactionSource,
            content = content
        )
    }
}

@Preview(group = "clickable", showBackground = true)
@Composable
private fun ClickablePreview() {
    RecipesBookTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardComponent(
                onClick = {}
            ) {
                DummyCardContent(text = "click me")
            }
            CardComponent(
                onClick = {},
                enforceTouchTargetSize = false
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "small chip",
                )
            }
        }
    }
}

@Composable
private fun DummyCardContent(text: String) {
    Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
        Text(text = text,)
    }
}