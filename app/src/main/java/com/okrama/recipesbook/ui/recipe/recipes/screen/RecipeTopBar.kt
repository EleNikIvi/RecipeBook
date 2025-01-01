package com.okrama.recipesbook.ui.recipe.recipes.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.components.filterrail.FilterRail
import com.okrama.recipesbook.ui.core.components.topappbar.LargeTopAppBar
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.recipe.recipes.RecipesScreenState


@Composable
fun RecipesToolbar(
    contentState: RecipesScreenState,
    isCollapsed: Boolean = false,
    onAddNewRecipe: () -> Unit = {},
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
    onFilterCategorySelected: (Long) -> Unit = {},
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
        Column(modifier = Modifier.heightIn(min = 16.dp)) {
            FilterRail(
                filterItems = contentState.categories,
                selectedItem = contentState.selectedCategory,
                onFilterCategorySelected = onFilterCategorySelected,
                onAddNewCategory = onAddNewCategory,
                contentPadding = PaddingValues(
                    start = 16.dp, end = 16.dp,
                    top = 12.dp
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipesToolbarPreview(
    @PreviewParameter(RecipeScreenStateProvider::class)
    screenState: RecipesScreenState
) {
    RecipesBookTheme {
        RecipesToolbar(screenState)
    }
}

