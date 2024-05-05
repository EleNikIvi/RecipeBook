package com.okrama.recipesbook.ui.core.components.inputfields

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.okrama.recipesbook.ui.addcategory.MAX_CATEGORY_TITLE_CHAR
import com.okrama.recipesbook.ui.core.theme.Green1
import com.okrama.recipesbook.ui.core.theme.Grey0
import com.okrama.recipesbook.ui.core.theme.Grey5
import com.okrama.recipesbook.ui.core.theme.InteractiveColor

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
        shape = RoundedCornerShape(4.dp),
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
        shape = RoundedCornerShape(4.dp),
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
    val interactiveColor = InteractiveColor
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
    textColor = Green1,
    containerColor = Grey0,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    focusedLabelColor = Grey5,
    unfocusedLabelColor = Grey5,
    placeholderColor = Grey5,
    disabledPlaceholderColor = Grey5,
)
