package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.backgroundLight
import com.okrama.recipesbook.ui.recipes.RecipesScreenState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    screenState: RecipesScreenState,
    onAddNewRecipe: () -> Unit,
    onEditRecipe: (Long) -> Unit,
    onRecipeSelected: (Long) -> Unit,
    onSearchFieldClear: () -> Unit,
    onSearchTermChange: (String) -> Unit,
    onDeleteRecipe: (Long) -> Unit,
    onRecipeCategoryChange: (Category) -> Unit,
    onAddNewCategory: () -> Unit,
) {
    val listState = rememberLazyGridState()
    val isCollapsed = isToolBarCollapsed(listState)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = backgroundLight,
        topBar = {
            RecipesToolbar(
                screenState,
                isCollapsed = isCollapsed,
                onAddNewRecipe = onAddNewRecipe,
                onSearchTermChange = onSearchTermChange,
                onSearchFieldClear = onSearchFieldClear,
                onFilterCategorySelected = onRecipeCategoryChange,
                onAddNewCategory = onAddNewCategory,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 130.dp),
                state = listState,
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (screenState.recipes.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        RecipesEmptyMessage()
                    }
                } else {
                    items(
                        items = screenState.recipes,
                        key = { it.recipeId },
                    ) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onRecipeSelected = onRecipeSelected,
                            onDeleteRecipe = onDeleteRecipe,
                            onEditRecipe = onEditRecipe,
                        )
                    }
                }

            }
        }
    }
}

@DevicePreviews
@Composable
private fun RecipesScreenPreview(
    @PreviewParameter(RecipeScreenStateProvider::class)
    screenState: RecipesScreenState
) {
    RecipesBookTheme {
        RecipesScreen(
            screenState = screenState,
            onAddNewRecipe = {},
            onRecipeSelected = {},
            onSearchTermChange = {},
            onSearchFieldClear = {},
            onDeleteRecipe = {},
            onEditRecipe = {},
            onRecipeCategoryChange = {},
            onAddNewCategory = {},
        )
    }
}
