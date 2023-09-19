package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.ui.CircularProgressDialog
import com.okrama.recipesbook.ui.core.ui.DevicePreviews
import com.okrama.recipesbook.ui.core.ui.SearchFieldComponent
import com.okrama.recipesbook.ui.core.ui.theme.Green3
import com.okrama.recipesbook.ui.core.ui.theme.Yellow1
import com.okrama.recipesbook.ui.core.ui.theme.Green0
import com.okrama.recipesbook.ui.core.ui.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.ui.theme.Grey1
import com.okrama.recipesbook.ui.recipes.RecipesScreenState
import com.okrama.recipesbook.ui.recipes.RecipesViewModel

@Composable
fun RecipesScreen(
    onAddNewRecipe: () -> Unit,
    onRecipeSelected: (Long) -> Unit,
    viewModel: RecipesViewModel,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipesScreen(
    screenState: RecipesScreenState,
    onAddNewRecipe: () -> Unit,
    onRecipeSelected: (Long) -> Unit,
    onSearchTermChange: (String) -> Unit,
    onSearchFieldClear: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            RecipesToolbar(
                screenState.searchRequest,
                onAddNewRecipe,
                onSearchTermChange,
                onSearchFieldClear,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Grey1),
            contentAlignment = Alignment.TopCenter
        ) {
            when (screenState) {
                is RecipesScreenState.Initial -> {}
                is RecipesScreenState.Loaded -> {
                    RecipesLoadedContent(
                        loadedState = screenState,
                        onRecipeSelected = onRecipeSelected,
                    )
                }

                is RecipesScreenState.Loading -> {
                    CircularProgressDialog(message = stringResource(id = R.string.message_wait))
                }

                is RecipesScreenState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
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

@Composable
private fun RecipesToolbar(
    searchRequest: String,
    onAddNewRecipe: () -> Unit,
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(Yellow1, Green0)))
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.app_title),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchFieldComponent(
                searchTerm = searchRequest,
                placeholder = stringResource(id = R.string.search_hint),
                onSearchTermChange = onSearchTermChange,
                onSearchFieldClear = onSearchFieldClear,
                modifier = Modifier
                    .weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onAddNewRecipe) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.TwoTone.Add),
                    contentDescription = stringResource(id = R.string.add_new_recipe),
                    tint = Green3,
                    modifier = Modifier
                        .size(50.dp),
                )
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
