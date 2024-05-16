package com.okrama.recipesbook.ui.addrecipe.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.okrama.recipesbook.ui.addrecipe.DropdownField
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.components.inputfields.SpinnerComponent
import com.okrama.recipesbook.ui.core.components.inputfields.getStringValue
import com.okrama.recipesbook.ui.core.theme.Green0
import com.okrama.recipesbook.ui.core.theme.Grey0
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

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
            selectedCategory = getStringValue(
                categoriesDropdown.valueResId,
                categoriesDropdown.value
            ),
            spinnerItems = categoriesDropdown.spinnerItems,
            onSelectionChanged = onCategoryChange,
            actionIcon = {
                Spacer(modifier = Modifier.width(16.dp))
                CardComponent(
                    onClick = { onAddNewCategory() },
                    backgroundColor = Grey0,
                    elevation = RecipesBookTheme.elevation.small,
                    enforceTouchTargetSize = false
                ) {
                    Column {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.TwoTone.Add),
                            modifier = Modifier
                                .widthIn(min = 36.dp)
                                .heightIn(min = 36.dp),
                            contentDescription = null,
                            tint = Green0,
                        )
                    }
                }
            }
        )
    }
}