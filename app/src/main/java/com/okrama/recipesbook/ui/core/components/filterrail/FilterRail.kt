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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.components.inputfields.getStringValue
import com.okrama.recipesbook.ui.core.theme.Green0
import com.okrama.recipesbook.ui.core.theme.Green1
import com.okrama.recipesbook.ui.core.theme.Green2
import com.okrama.recipesbook.ui.core.theme.Grey2
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.Yellow1

@Composable
fun FilterRail(
    onFilterCategorySelected: (Category) -> Unit,
    onAddNewCategory: () -> Unit,
    filterCategories: List<Category>,
    selectedCategory: Category,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    scrollState: ScrollState = rememberScrollState(),
) {
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(contentPadding)
    ) {
        filterCategories.forEach { category ->
            FilterChip(
                title = getStringValue(category.titleResId, category.title),
                isSelected = category == selectedCategory,
                onClick = { onFilterCategorySelected(category) },
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        AddNewFilterChip(
            onClick = { onAddNewCategory() },
        )
    }
}

@Composable
private fun FilterChip(
    title: String,
    onClick: () -> Unit,
    isSelected: Boolean,
) {
    CardComponent(
        onClick = onClick,
        backgroundColor = if (isSelected) Yellow1 else Color.Transparent,
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
                color = if (isSelected) Green1 else Green2,
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
        backgroundColor = Green0,
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
                tint = Grey2,
            )
        }
    }
}

@Preview(group = "appearance compact", showBackground = true)
@Composable
private fun FilterRailCompactSizePreview(
    @PreviewParameter(FilterRailDataProvider::class)
    categories: List<Category>
) {
    RecipesBookTheme {
        FilterRail(
            onFilterCategorySelected = {},
            onAddNewCategory = {},
            filterCategories = categories,
            selectedCategory = categories.first(),
        )
    }
}

@Preview(group = "appearance large", showBackground = true)
@Composable
private fun FilterRailLargeSizePreview(
    @PreviewParameter(FilterRailDataProvider::class)
    categories: List<Category>
) {
    RecipesBookTheme {
        FilterRail(
            onFilterCategorySelected = {},
            onAddNewCategory = {},
            filterCategories = categories,
            selectedCategory = categories.first(),
        )
    }
}

@Preview(group = "appearance compact", showBackground = true)
@Composable
private fun FilterChipCompactSizePreview(
    @PreviewParameter(FilterRailDataProvider::class)
    categories: List<Category>
) {
    RecipesBookTheme {
        Column(Modifier.padding(horizontal = 16.dp)) {
            FilterChip(
                title = categories[0].title ?: "",
                onClick = { },
                isSelected = true,
            )
            FilterChip(
                title = categories[1].title ?: "",
                onClick = { },
                isSelected = false,
            )
        }
    }
}

@Preview(group = "appearance large", showBackground = true)
@Composable
private fun FilterChipLargeSizePreview(
    @PreviewParameter(FilterRailDataProvider::class)
    categories: List<Category>
) {
    RecipesBookTheme {
        Column(Modifier.padding(horizontal = 16.dp)) {
            FilterChip(
                title = categories[0].title ?: "",
                onClick = { },
                isSelected = true,
            )
            FilterChip(
                title = categories[1].title ?: "",
                onClick = { },
                isSelected = false,
            )
        }
    }
}
