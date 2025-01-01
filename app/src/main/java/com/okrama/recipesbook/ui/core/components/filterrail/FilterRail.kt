package com.okrama.recipesbook.ui.core.components.filterrail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.components.filterrail.model.FilterRailItem
import com.okrama.recipesbook.ui.core.components.inputfields.getStringValue
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.inversePrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.onPrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.onPrimaryLight
import com.okrama.recipesbook.ui.core.theme.primaryContainerLight
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FilterRail(
    filterItems: ImmutableList<FilterRailItem>,
    selectedItem: FilterRailItem,
    onFilterCategorySelected: (Long) -> Unit,
    onAddNewCategory: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    scrollState: ScrollState = rememberScrollState(),
) {
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        filterItems.forEach { item ->
            FilterChip(
                title = getStringValue(item.valueResId, item.value),
                isSelected = item == selectedItem,
                onClick = {
                    onFilterCategorySelected(item.id)
                },
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        AddNewFilterChip(
            onClick = onAddNewCategory,
        )
    }
}

@Composable
private fun FilterChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    CardComponent(
        onClick = onClick,
        backgroundColor = if (isSelected) inversePrimaryContainerLight else Color.Transparent,
        elevation = if (isSelected) RecipesBookTheme.elevation.small else 0.dp,
        enforceTouchTargetSize = false
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        ) {
            Text(
                text = title,
                style = RecipesBookTheme.typography.bodyMediumStrong,
                color = onPrimaryLight,
                modifier = Modifier
                    .semantics { contentDescription = title },
            )
        }
    }
}

@Composable
private fun AddNewFilterChip(
    onClick: () -> Unit,
) {
    CardComponent(
        onClick = onClick,
        backgroundColor = primaryContainerLight,
        elevation = RecipesBookTheme.elevation.small,
        enforceTouchTargetSize = false
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.TwoTone.Add),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
                tint = onPrimaryContainerLight,
            )
        }
    }
}

@Preview(group = "appearance compact", showBackground = true)
@Composable
private fun FilterRailCompactSizePreview(
    @PreviewParameter(FilterRailPreviewDataProvider::class)
    items: ImmutableList<FilterRailItem>
) {
    RecipesBookTheme {
        FilterRail(
            onFilterCategorySelected = {},
            onAddNewCategory = {},
            filterItems = items,
            selectedItem = items.first(),
        )
    }
}

@Preview(group = "appearance large", showBackground = true)
@Composable
private fun FilterRailLargeSizePreview(
    @PreviewParameter(FilterRailPreviewDataProvider::class)
    items: ImmutableList<FilterRailItem>
) {
    RecipesBookTheme {
        FilterRail(
            onFilterCategorySelected = {},
            onAddNewCategory = {},
            filterItems = items,
            selectedItem = items.first(),
        )
    }
}

@Preview(group = "appearance compact", showBackground = true)
@Composable
private fun FilterChipCompactSizePreview(
    @PreviewParameter(FilterRailPreviewDataProvider::class)
    items: List<FilterRailItem>
) {
    RecipesBookTheme {
        Column(Modifier.padding(horizontal = 16.dp)) {
            FilterChip(
                title = items[0].value ?: "",
                onClick = { },
                isSelected = true,
            )
            FilterChip(
                title = items[1].value ?: "",
                onClick = { },
                isSelected = false,
            )
        }
    }
}

@Preview(group = "appearance large", showBackground = true)
@Composable
private fun FilterChipLargeSizePreview(
    @PreviewParameter(FilterRailPreviewDataProvider::class)
    items: List<FilterRailItem>
) {
    RecipesBookTheme {
        Column(Modifier.padding(horizontal = 16.dp)) {
            FilterChip(
                title = items[0].value ?: "",
                onClick = { },
                isSelected = true,
            )
            FilterChip(
                title = items[1].value ?: "",
                onClick = { },
                isSelected = false,
            )
        }
    }
}
