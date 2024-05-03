package com.okrama.recipesbook.ui.core.components.inputfields

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.theme.Grey5
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import timber.log.Timber

private val ICON_SIZE = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFieldComponent(
    searchTerm: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    onSearchTermChange: (String) -> Unit,
    onSearchFieldClear: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    searchClearContentDescription: String = stringResource(id = R.string.button_clear_description),
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = searchTerm,
        onValueChange = onSearchTermChange,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(image = Icons.TwoTone.Search),
                contentDescription = "",
                modifier = Modifier.size(ICON_SIZE),
                tint = Grey5,
            )
        },
        trailingIcon = if (searchTerm.isNotEmpty()) {
            {
                IconButton(onClick = onSearchFieldClear) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.TwoTone.Close),
                        contentDescription = searchClearContentDescription,
                        modifier = Modifier.size(ICON_SIZE),
                        tint = Grey5,
                    )
                }
            }
        } else {
            null
        },
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    style = RecipesBookTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        },
        textStyle = RecipesBookTheme.typography.bodyLarge,
        enabled = true,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(14.dp),
        colors = textFieldColors(),
        modifier = modifier
            .hoverable(
                enabled = enabled,
                interactionSource = interactionSource,
            )
            .onFocusChanged {
                if (it.hasFocus) {
                    Timber.d("Search field focused")
                }
            },
    )
}

@Preview(group = "Search empty")
@Composable
private fun SearchFieldEmptyPreview() {
    RecipesBookTheme {
        Column(Modifier.padding(16.dp)) {
            SearchFieldComponent(
                searchTerm = "",
                placeholder = "Empty enabled",
                onSearchTermChange = {},
                onSearchFieldClear = {},
                enabled = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchFieldComponent(
                searchTerm = "",
                placeholder = "Empty disabled",
                onSearchTermChange = {},
                onSearchFieldClear = {},
                enabled = false,
            )
        }
    }
}

@Preview(group = "Search populated")
@Composable
private fun SearchFieldPopulatedPreview() {
    RecipesBookTheme {
        Column(Modifier.padding(16.dp)) {
            SearchFieldComponent(
                searchTerm = "Populated enabled",
                onSearchTermChange = {},
                onSearchFieldClear = {},
                enabled = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchFieldComponent(
                searchTerm = "Populated disabled",
                onSearchTermChange = {},
                onSearchFieldClear = {},
                enabled = false,
            )
        }
    }
}
