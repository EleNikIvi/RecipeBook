package com.okrama.recipesbook.ui.addrecipe.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.addrecipe.AddRecipeScreenState
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.bringIntoView
import com.okrama.recipesbook.ui.core.components.EditScreenContainer
import com.okrama.recipesbook.ui.core.components.RecipeGalleryImage
import com.okrama.recipesbook.ui.core.components.inputfields.RecipeTextField
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

private val GALLERY_IMAGE_SIZE = 300.dp

@Composable
fun AddRecipeScreen(
    state: AddRecipeScreenState,
    onImageAdded: (String) -> Unit,
    onRecipeNameChange: (String) -> Unit,
    onRecipeDescriptionChange: (String) -> Unit,
    onCategoryChange: (Long) -> Unit,
    onIngredientsChange: (String) -> Unit,
    onAddNewCategory: () -> Unit,
    onSaveRecipe: () -> Unit,
    upPress: () -> Unit,
) {
    if (state is AddRecipeScreenState.Initial) {
        val scrollState = rememberScrollState()
        EditScreenContainer(
            title = stringResource(id = R.string.title_new_recipe),
            upPress = upPress,
            onSave = onSaveRecipe,
            canSave = state.canSave,
            scrollState = scrollState,
        ) {
            RecipeGalleryImage(
                modifier = Modifier
                    .height(GALLERY_IMAGE_SIZE)
                    .width(GALLERY_IMAGE_SIZE)
                    .padding(16.dp),
                imageUrl = state.imageUrl,
                onImageAdded = onImageAdded,
            )
            CategorySpinner(
                modifier = Modifier.padding(horizontal = 16.dp),
                categoriesDropdown = state.categoriesDropdown,
                onCategoryChange = onCategoryChange,
                onAddNewCategory = onAddNewCategory,
            )
            Spacer(modifier = Modifier.height(16.dp))
            RecipeTextField(
                text = state.title,
                modifier = Modifier.padding(horizontal = 16.dp),
                onTextChange = onRecipeNameChange,
                placeholder = stringResource(id = R.string.recipe_name_placeholder),
                imeAction = ImeAction.Next,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            RecipeTextField(
                text = state.description,
                modifier = Modifier.padding(horizontal = 16.dp),
                onTextChange = onRecipeDescriptionChange,
                placeholder = stringResource(id = R.string.recipe_description_placeholder),
                imeAction = ImeAction.Next,
            )
            RecipeTextField(
                text = state.ingredients,
                modifier = Modifier
                    .padding(16.dp)
                    .bringIntoView(scrollState),
                onTextChange = onIngredientsChange,
                placeholder = stringResource(id = R.string.recipe_ingredients_placeholder),
                imeAction = ImeAction.Default,
            )
        }
    } else {
        LaunchedEffect(Unit) {
            upPress()
        }
    }
}

@DevicePreviews
@Composable
private fun AddRecipeScreenPreview(
    @PreviewParameter(AddRecipeScreenStateProvider::class)
    screenState: AddRecipeScreenState
) {
    RecipesBookTheme {
        AddRecipeScreen(
            state = screenState,
            onImageAdded = {},
            onRecipeNameChange = {},
            onRecipeDescriptionChange = {},
            onCategoryChange = {},
            onIngredientsChange = {},
            onAddNewCategory = {},
            onSaveRecipe = {},
            upPress = {},
        )
    }
}
