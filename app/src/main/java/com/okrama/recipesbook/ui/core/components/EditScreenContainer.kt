package com.okrama.recipesbook.ui.core.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.okrama.recipesbook.ui.addrecipe.AddRecipeScreenState
import com.okrama.recipesbook.ui.addrecipe.screen.AddRecipeScreenStateProvider
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenContainer(
    title: String,
    canSave: Boolean,
    scrollState: ScrollState = rememberScrollState(),
    upPress: () -> Unit,
    onSave: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RecipeTopAppBar(
                title = title,
                upPress = upPress,
                onSave = onSave,
                canSave = canSave,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .imePadding()
                .padding(paddingValues)
                .fillMaxSize()
                .background(primaryLight)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@DevicePreviews
@Composable
private fun EditScreenContainerPreview(
    @PreviewParameter(AddRecipeScreenStateProvider::class)
    screenState: AddRecipeScreenState
) {
    RecipesBookTheme {
        EditScreenContainer(
            title = "New Category",
            upPress = {},
            onSave = {},
            canSave = true,
            content = {},
        )
    }
}