package com.okrama.recipesbook.ui.addrecipe.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.ui.DevicePreviews
import com.okrama.recipesbook.ui.core.ui.components.RecipeGalleryImage
import com.okrama.recipesbook.ui.core.ui.theme.Green0
import com.okrama.recipesbook.ui.core.ui.theme.Green3
import com.okrama.recipesbook.ui.core.ui.theme.Grey0
import com.okrama.recipesbook.ui.core.ui.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.ui.theme.Yellow1
import com.okrama.recipesbook.ui.core.ui.theme.Yellow4
import com.okrama.recipesbook.ui.addrecipe.AddRecipeScreenState
import com.okrama.recipesbook.ui.addrecipe.AddRecipeViewModel

@Composable
fun AddRecipeScreen(
    upPress: () -> Unit,
    viewModel: AddRecipeViewModel,
) {
    val onImageAdded: (String) -> Unit = viewModel::onImageAdded
    val onRecipeNameChange: (String) -> Unit = viewModel::onRecipeNameChange
    val onRecipeDescriptionChange: (String) -> Unit = viewModel::onRecipeDescriptionChange
    val onSaveRecipe: () -> Unit = viewModel::createRecipe
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    AddRecipeScreen(
        state,
        onImageAdded,
        onRecipeNameChange,
        onRecipeDescriptionChange,
        onSaveRecipe,
        upPress,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRecipeScreen(
    addRecipeState: AddRecipeScreenState,
    onImageAdded: (String) -> Unit,
    onRecipeNameChange: (String) -> Unit,
    onRecipeDescriptionChange: (String) -> Unit,
    onSaveRecipe: () -> Unit,
    upPress: () -> Unit,
) {
    if (addRecipeState is AddRecipeScreenState.Initial) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                AddRecipeTopAppbar(
                    upPress,
                    onSaveRecipe,
                    addRecipeState.canSave,
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
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecipeGalleryImage(
                        modifier = Modifier
                            .height(300.dp)
                            .width(300.dp)
                            .padding(bottom = 16.dp),
                        imageUrl = addRecipeState.imageUrl,
                        onImageAdded = onImageAdded,
                    )
                    AddRecipeTextField(
                        text = addRecipeState.title,
                        onTextChange = onRecipeNameChange,
                        placeholder = stringResource(id = R.string.recipe_name_placeholder),
                        singleLine = true,
                    )

                    AddRecipeTextField(
                        text = addRecipeState.description,
                        onTextChange = onRecipeDescriptionChange,
                        placeholder = stringResource(id = R.string.recipe_description_placeholder),
                    )
                }
            }
        }
    } else {
        upPress()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRecipeTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    singleLine: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = {
            Text(
                text = placeholder,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        enabled = true,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        interactionSource = interactionSource,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Grey0,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .hoverable(
                enabled = enabled,
                interactionSource = interactionSource,
            )
            .onFocusChanged {
                if (it.hasFocus) {
                    Log.d("tag", "Text field focused")
                }
            },
    )
}

@DevicePreviews
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(AddRecipeScreenStateProvider::class)
    screenState: AddRecipeScreenState
) {
    RecipesBookTheme {
        AddRecipeScreen(
            addRecipeState = screenState,
            onImageAdded = {},
            onRecipeNameChange = {},
            onRecipeDescriptionChange = {},
            onSaveRecipe = {},
            upPress = {},
        )
    }
}
