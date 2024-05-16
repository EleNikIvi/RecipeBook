package com.okrama.recipesbook.ui.addrecipe.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.addrecipe.AddRecipeScreenState
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.RecipeGalleryImage
import com.okrama.recipesbook.ui.core.components.inputfields.RecipeTextField
import com.okrama.recipesbook.ui.core.theme.Green0
import com.okrama.recipesbook.ui.core.theme.Green3
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.Yellow1
import com.okrama.recipesbook.ui.core.theme.Yellow4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    state: AddRecipeScreenState,
    onImageAdded: (String) -> Unit,
    onRecipeNameChange: (String) -> Unit,
    onRecipeDescriptionChange: (String) -> Unit,
    onCategoryChange: (Long) -> Unit,
    onAddNewCategory: () -> Unit,
    onSaveRecipe: () -> Unit,
    upPress: () -> Unit,
) {
    if (state is AddRecipeScreenState.Initial) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                AddRecipeTopAppbar(
                    upPress,
                    onSaveRecipe,
                    state.canSave,
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Yellow1)
            ) {
                Column(
                    modifier = Modifier
                        .systemBarsPadding()
                        .imePadding()
                        .fillMaxSize()
                        .background(Green0)
                        //.padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecipeGalleryImage(
                        modifier = Modifier
                            .height(300.dp)
                            .width(300.dp)
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
                        singleLine = true,
                    )
                    RecipeTextField(
                        text = state.description,
                        modifier = Modifier.padding(16.dp),
                        onTextChange = onRecipeDescriptionChange,
                        placeholder = stringResource(id = R.string.recipe_description_placeholder),
                    )
                }
            }
        }
    } else {
        LaunchedEffect(Unit) {
            upPress()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRecipeTopAppbar(
    upPress: () -> Unit,
    onSaveRecipe: () -> Unit,
    canSave: Boolean,
) {
    val focusManager = LocalFocusManager.current
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.title_new_recipe),
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(Yellow1, Green0))),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            IconButton(
                onClick = upPress,
                enabled = true,
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                    tint = Green3,
                    contentDescription = stringResource(id = R.string.navigate_back),
                )
            }
        },
        actions = {
            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    onSaveRecipe()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Yellow4,
                ),
                enabled = canSave,
            ) {
                Text(
                    text = stringResource(id = R.string.button_save),
                    fontSize = 20.sp,
                )
            }
        },
    )
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
            onAddNewCategory = {},
            onSaveRecipe = {},
            upPress = {},
        )
    }
}
