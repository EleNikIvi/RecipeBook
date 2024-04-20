package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.CircularProgressDialog
import com.okrama.recipesbook.ui.core.theme.Grey1
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.recipes.RecipesScreenState
import com.okrama.recipesbook.ui.recipes.RecipesViewModel


@Composable
fun RecipesScreen(
    onAddNewRecipe: () -> Unit,
    onRecipeSelected: (Long) -> Unit,
    viewModel: RecipesViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val onSearchTermChange: (String) -> Unit = viewModel::onSearchTermChange
    val onSearchFieldClear: () -> Unit = viewModel::onSearchFieldClear
    RecipesScreen(
        screenState,
        onAddNewRecipe,
        onRecipeSelected,
        onSearchTermChange,
        onSearchFieldClear,
    )
}

@Composable
private fun RecipesScreen(
    screenState: RecipesScreenState,
    onAddNewRecipe: () -> Unit,
    onRecipeSelected: (Long) -> Unit,
    onSearchTermChange: (String) -> Unit,
    onSearchFieldClear: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey1),
        contentAlignment = Alignment.TopCenter
    ) {
        val listState = rememberLazyGridState()

        val isCollapsed = isToolBarCollapsed(listState)
        CollapsedToolBar(
            modifier = Modifier.zIndex(2f),
            isCollapsed = isCollapsed,
            onAddNewRecipe = onAddNewRecipe,
        )
        Title(isCollapsed)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                RecipesToolbar(
                    screenState.searchRequest,
                    isCollapsed = isCollapsed,
                    onAddNewRecipe,
                    onSearchTermChange,
                    onSearchFieldClear,
                )
            }

            when (screenState) {
                is RecipesScreenState.Initial -> {}
                is RecipesScreenState.Loaded -> {
                    items(
                        items = screenState.recipes,
                        key = { it.id },
                    ) { recipe ->
                        Column(
                            modifier = Modifier
                                .padding(PaddingValues(all = 8.dp))
                        ) {
                            RecipeItem(recipe, onRecipeSelected)
                        }
                    }
                }

                is RecipesScreenState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CircularProgressDialog(message = stringResource(id = R.string.message_wait))
                    }
                }

                is RecipesScreenState.Error -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(id = R.string.message_error),
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
        )
    }
}
