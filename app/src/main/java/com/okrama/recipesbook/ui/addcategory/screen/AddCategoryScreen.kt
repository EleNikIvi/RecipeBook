package com.okrama.recipesbook.ui.addcategory.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.addcategory.AddCategoryScreenState
import com.okrama.recipesbook.ui.addcategory.MAX_CATEGORY_TITLE_CHAR
import com.okrama.recipesbook.ui.addrecipe.screen.AddRecipeScreenStateProvider
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.SmallTopAppBarScreenContainer
import com.okrama.recipesbook.ui.core.components.inputfields.RecipeTextFieldWithLimit
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

@Composable
fun AddCategoryScreen(
    state: AddCategoryScreenState,
    onCategoryNameChange: (String) -> Unit,
    onSaveCategory: () -> Unit,
    upPress: () -> Unit,
    upPressWithResult: (Long) -> Unit,
) {
    if (state is AddCategoryScreenState.Initial) {
        SmallTopAppBarScreenContainer(
            title = stringResource(id = R.string.title_new_category),
            upPress = upPress,
            onAction = onSaveCategory,
            actionButtonEnabled = state.canSave,
        ) {
            RecipeTextFieldWithLimit(
                modifier = Modifier.padding(16.dp),
                text = state.title,
                onTextChange = onCategoryNameChange,
                placeholder = stringResource(id = R.string.category_name_placeholder),
                singleLine = true,
                maxTitleLength = MAX_CATEGORY_TITLE_CHAR,
            )
        }
    } else if (state is AddCategoryScreenState.Saved) {
        LaunchedEffect(Unit) {
            upPressWithResult(state.categoryId)
        }
    }
}

@DevicePreviews
@Composable
private fun AddCategoryScreenPreview(
    @PreviewParameter(AddRecipeScreenStateProvider::class)
    screenState: AddCategoryScreenState
) {
    RecipesBookTheme {
        AddCategoryScreen(
            state = screenState,
            onCategoryNameChange = {},
            onSaveCategory = {},
            upPress = {},
            upPressWithResult = {},
        )
    }
}