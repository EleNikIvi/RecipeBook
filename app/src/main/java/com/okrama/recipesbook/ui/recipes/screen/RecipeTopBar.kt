package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.components.filterrail.FilterRail
import com.okrama.recipesbook.ui.core.components.topappbar.LargeTopAppBar
import com.okrama.recipesbook.ui.recipes.RecipesScreenState


@Composable
fun RecipesToolbar(
    contentState: RecipesScreenState,
    isCollapsed: Boolean,
    onAddNewRecipe: () -> Unit = {},
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
    onFilterCategorySelected: (Category) -> Unit = {},
    onAddNewCategory: () -> Unit = {},
) {
    LargeTopAppBar(
        search = contentState.search,
        title = stringResource(id = R.string.app_title),
        isCollapsed = isCollapsed,
        searchPlaceholder = stringResource(id = R.string.search_recipe_hint),
        onAction = onAddNewRecipe,
        onSearchTermChange = onSearchTermChange,
        onSearchFieldClear = onSearchFieldClear,
    ) {
        FilterRail(
            contentState = contentState,
            onFilterCategorySelected = onFilterCategorySelected,
            onAddNewCategory = onAddNewCategory,
        )
    }
}

@Composable
private fun FilterRail(
    contentState: RecipesScreenState,
    onFilterCategorySelected: (Category) -> Unit,
    onAddNewCategory: () -> Unit,
) {
    val filterRailScrollState = rememberScrollState()

    Column(modifier = Modifier.heightIn(min = 16.dp)) {
        FilterRail(
            filterCategories = contentState.categories,
            selectedCategory = contentState.selectedCategory,
            onFilterCategorySelected = onFilterCategorySelected,
            onAddNewCategory = onAddNewCategory,
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp,
                top = 12.dp
            ),
            scrollState = filterRailScrollState,
        )
    }
}

