package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.theme.Grey1
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
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
        containerColor = Grey1,
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (screenState.recipes.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { // TODO Empty message
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(id = R.string.message_empty),
                            )
                        }
                    }
                } else {
                    items(
                        items = screenState.recipes,
                        key = { it.recipeId },
                    ) { recipe ->
                        Column(
                            modifier = Modifier
                                .padding(PaddingValues(all = 8.dp))
                        ) {
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
