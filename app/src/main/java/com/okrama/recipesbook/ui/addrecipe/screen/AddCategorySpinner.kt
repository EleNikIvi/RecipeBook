package com.okrama.recipesbook.ui.addrecipe.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.components.inputfields.SpinnerComponent
import com.okrama.recipesbook.ui.core.components.inputfields.getStringValue
import com.okrama.recipesbook.ui.core.components.inputfields.model.DropdownField
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.primaryLight

@Composable
fun CategorySpinner(
    modifier: Modifier = Modifier,
    categoriesDropdown: DropdownField,
    onCategoryChange: (Long) -> Unit,
    onAddNewCategory: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        SpinnerComponent(
            label = stringResource(id = R.string.category_label),
            selectedItem = getStringValue(
                categoriesDropdown.valueResId,
                categoriesDropdown.value
            ),
            spinnerItems = categoriesDropdown.spinnerItems,
            onSelectionChanged = onCategoryChange,
            actionIcon = {
                Spacer(modifier = Modifier.width(16.dp))
                CardComponent(
                    onClick = { onAddNewCategory() },
                    contentColor = primaryLight,
                    elevation = RecipesBookTheme.elevation.medium,
                ) {
                    Column {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.TwoTone.Add),
                            modifier = Modifier
                                .widthIn(min = 36.dp)
                                .heightIn(min = 36.dp),
                            contentDescription = null,
                        )
                    }
                }
            }
        )
    }
}