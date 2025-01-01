package com.okrama.recipesbook.ui.core.components.inputfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.ui.core.components.inputfields.model.SpinnerItem
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.backgroundLight
import com.okrama.recipesbook.ui.core.theme.onSurfaceLight
import com.okrama.recipesbook.ui.core.theme.primaryLight
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerComponent(
    label: String,
    modifier: Modifier = Modifier,
    selectedItem: String,
    spinnerItems: ImmutableList<SpinnerItem>,
    onSelectionChanged: (Long) -> Unit,
    actionIcon: @Composable (RowScope.() -> Unit) = {},
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            modifier = modifier
                .weight(1f),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = {
                    Text(
                        text = label,
                    )
                },
                trailingIcon = { SpinnerIcon(expanded = expanded) },
                colors = textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundLight),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                spinnerItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = getStringValue(item.valueResId, item.value),
                                color = onSurfaceLight,
                                style = RecipesBookTheme.typography.bodyLarge,
                            )
                        },
                        onClick = {
                            onSelectionChanged(item.id)
                            expanded = false
                        }
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actionIcon()
        }
    }
}

@Composable
private fun SpinnerIcon(
    expanded: Boolean,
) {
    Icon(
        painter = rememberVectorPainter(image = Icons.TwoTone.KeyboardArrowDown),
        contentDescription = null,
        modifier = Modifier
            .padding(start = 12.dp, end = 4.dp)
            .rotate(
                if (expanded)
                    180f
                else
                    0f
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun SpinnerButtonsPreview() {
    RecipesBookTheme {
        Row {
            SpinnerIcon(expanded = true)
            SpinnerIcon(expanded = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnabledSpinnerPreview() {
    RecipesBookTheme {
        Box {
            var spinnerSelectedIndex by rememberSaveable { mutableLongStateOf(1L) }
            SpinnerComponent(
                label = "Enabled spinner",
                selectedItem = OPTIONS[0].value ?: "",
                spinnerItems = OPTIONS,
                onSelectionChanged = { selectedIndex ->
                    spinnerSelectedIndex = selectedIndex
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DisabledSpinnerPreview() {
    RecipesBookTheme {
        Box {
            SpinnerComponent(
                label = "Disabled label",
                selectedItem = OPTIONS[0].value ?: "",
                spinnerItems = OPTIONS,
                onSelectionChanged = {  /*no-op*/ },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpinnerWithActionButtonPreview() {
    RecipesBookTheme {
        Box {
            var spinnerSelectedIndex by rememberSaveable { mutableLongStateOf(1L) }
            SpinnerComponent(
                label = "Spinner with action button",
                selectedItem = OPTIONS[0].value ?: "",
                spinnerItems = OPTIONS,
                onSelectionChanged = { selectedIndex ->
                    spinnerSelectedIndex = selectedIndex
                },
                actionIcon = {
                    IconButton(
                        onClick = { /* no-op */ },
                    ) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Rounded.Add),
                            tint = primaryLight,
                            contentDescription = "",
                        )
                    }
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpinnerWithPlaceholderTextPreview() {
    RecipesBookTheme {
        Box {
            var spinnerSelectedIndex by rememberSaveable { mutableLongStateOf(0L) }
            SpinnerComponent(
                label = "Spinner with placeholder text",
                selectedItem = OPTIONS[0].value ?: "",
                spinnerItems = OPTIONS,
                onSelectionChanged = { selectedIndex ->
                    spinnerSelectedIndex = selectedIndex
                },
                actionIcon = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpinnerWithLongTextPreview() {
    RecipesBookTheme {
        Box {
            var spinnerSelectedIndex by rememberSaveable { mutableLongStateOf(5L) }
            SpinnerComponent(
                label = "Spinner with long text",
                selectedItem = OPTIONS[0].value ?: "",
                spinnerItems = OPTIONS,
                onSelectionChanged = { selectedIndex ->
                    spinnerSelectedIndex = selectedIndex
                },
                actionIcon = {}
            )
        }
    }
}

private val OPTIONS = persistentListOf(
    SpinnerItem(0L, ""),
    SpinnerItem(1L, "second"),
    SpinnerItem(2L, "third"),
    SpinnerItem(3L, "fourth"),
    SpinnerItem(4L, "fifth"),
    SpinnerItem(5L, "678191"),
)