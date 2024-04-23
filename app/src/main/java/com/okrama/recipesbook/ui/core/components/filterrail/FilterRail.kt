package com.okrama.recipesbook.ui.core.components.filterrail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.theme.Green1
import com.okrama.recipesbook.ui.core.theme.Green2
import com.okrama.recipesbook.ui.core.theme.Grey0
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

@Composable
fun FilterRail(
    onFilterCategorySelected: (Category) -> Unit,
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
                title = category.titleResId?.let { stringResource(id = it) } ?: category.title
                ?: "",
                isSelected = category == selectedCategory,
                onClick = { onFilterCategorySelected(category) },
            )
        }
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
        backgroundColor = if (isSelected) Grey0 else Color.Transparent,
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
                fontSize = 12.sp,
                color = if (isSelected) Green1 else Green2,
                modifier = Modifier
                    .semantics { contentDescription = title },
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
