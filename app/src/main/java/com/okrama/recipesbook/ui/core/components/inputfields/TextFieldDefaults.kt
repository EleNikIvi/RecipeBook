package com.okrama.recipesbook.ui.core.components.inputfields

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.theme.InteractiveColor
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.onPrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.onSurfaceLight
import com.okrama.recipesbook.ui.core.theme.onSurfaceVariantLight
import com.okrama.recipesbook.ui.core.theme.surfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    singleLine: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(
                text = placeholder,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        enabled = true,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        interactionSource = interactionSource,
        colors = textFieldColors(),
        shape = RecipesBookTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .hoverable(
                enabled = enabled,
                interactionSource = interactionSource,
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTextFieldWithLimit(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    singleLine: Boolean = true,
    maxTitleLength: Int = 15,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = text,
        onValueChange = {
            if (it.length <= maxTitleLength) {
                onTextChange(it)
            }
        },
        supportingText = {
            Text(
                color = onPrimaryContainerLight,
                text = stringResource(id = R.string.max_length_format, text.length, maxTitleLength),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        enabled = true,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        interactionSource = interactionSource,
        colors = textFieldColors(),
        shape = RecipesBookTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .hoverable(
                enabled = enabled,
                interactionSource = interactionSource,
            ),
    )
}

@Composable
internal fun defaultTextSelectionColors(): TextSelectionColors {
    val interactiveColor = onPrimaryContainerLight
    return remember(interactiveColor) {
        TextSelectionColors(
            handleColor = interactiveColor,
            backgroundColor = interactiveColor.copy(alpha = .3f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = onSurfaceLight,
    containerColor = surfaceLight,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    focusedLabelColor = onSurfaceVariantLight,
    unfocusedLabelColor = onSurfaceVariantLight,
    placeholderColor = onSurfaceVariantLight,
    disabledPlaceholderColor = onSurfaceVariantLight,
)
