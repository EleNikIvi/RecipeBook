package com.okrama.recipesbook.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.dropShadow
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.onPrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.primaryContainerLight
import com.okrama.recipesbook.ui.core.theme.primaryLight

@Composable
fun ButtonAdd(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    largeAppearance: Boolean = false,
) {
    val buttonHeight by height(largeAppearance)
    val buttonTextSize by fontSize(largeAppearance)
    val horizontalGap by horizontalGap(largeAppearance)
    val contentPadding by contentPadding(largeAppearance)

    Button(
        modifier = modifier
            .dropShadow(
                elevation = 20.dp,
                color = primaryLight.copy(alpha = 0.2f),
                offsetY = 8.dp,
                cornerRadius = 8.dp,
            )
            .height(height = buttonHeight),
        shape = RecipesBookTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryContainerLight,
            contentColor = onPrimaryContainerLight,
        ),
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        Icon(
            painter = rememberVectorPainter(image = Icons.TwoTone.Add),
            contentDescription = stringResource(id = R.string.add_label),
        )
        Spacer(modifier = Modifier.width(horizontalGap))
        Text(
            modifier = Modifier
                .weight(weight = 1f, fill = false),
            text = stringResource(id = R.string.add_label),
            style = RecipesBookTheme.typography.buttonLarge,
            fontSize = buttonTextSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun contentPadding(largeAppearance: Boolean): State<PaddingValues> = rememberUpdatedState(
    newValue = PaddingValues(
        start = if (largeAppearance) 20.dp else 14.dp,
        end = if (largeAppearance) 24.dp else 16.dp,
    )
)

@Composable
private fun height(largeAppearance: Boolean): State<Dp> =
    rememberUpdatedState(newValue = if (largeAppearance) 56.dp else 36.dp)

@Composable
private fun fontSize(largeAppearance: Boolean): State<TextUnit> =
    rememberUpdatedState(newValue = if (largeAppearance) 18.sp else 16.sp)

@Composable
private fun horizontalGap(largeAppearance: Boolean): State<Dp> =
    rememberUpdatedState(newValue = if (largeAppearance) 12.dp else 10.dp)

@Composable
private fun iconSize(largeAppearance: Boolean): State<Dp> =
    rememberUpdatedState(newValue = if (largeAppearance) 18.dp else 14.dp)

@Preview(showBackground = true)
@Composable
fun ButtonAddPreview() {
    RecipesBookTheme {
        ButtonAdd(
            onClick = { },
            modifier = Modifier
                .wrapContentSize()
                .padding(20.dp),
            largeAppearance = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonAddWithLargeAppearancePreview() {
    RecipesBookTheme {
        ButtonAdd(
            onClick = { },
            modifier = Modifier
                .wrapContentSize()
                .padding(20.dp),
            largeAppearance = true
        )
    }
}