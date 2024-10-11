package com.okrama.recipesbook.ui.recipe.details.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.components.ImageComponent
import com.okrama.recipesbook.ui.core.components.button.ActionButton
import com.okrama.recipesbook.ui.core.components.dialog.AlertDialogComponent
import com.okrama.recipesbook.ui.core.components.inputfields.SpinnerComponent
import com.okrama.recipesbook.ui.core.components.inputfields.getStringValue
import com.okrama.recipesbook.ui.core.components.inputfields.model.DropdownField
import com.okrama.recipesbook.ui.core.components.inputfields.model.SpinnerItem
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.primaryContainerLight
import com.okrama.recipesbook.ui.core.theme.primaryLight
import com.okrama.recipesbook.ui.core.theme.radioButtonColors
import com.okrama.recipesbook.ui.recipe.details.Dialog
import com.okrama.recipesbook.ui.recipe.details.RecipeDetailsScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    state: RecipeDetailsScreenState,
    upPress: () -> Unit,
    onEditRecipe: (Long) -> Unit,
    onShowShoppingList: () -> Unit,
    onCreateShoppingList: () -> Unit,
    onUpdateShoppingList: (Long) -> Unit,
    onHideShoppingList: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(primaryContainerLight)
        ) {
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .imePadding()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageComponent(
                    imageUri = state.imageUrl,
                    modifier = Modifier.aspectRatio(1f),
                    contentDescription = stringResource(id = R.string.recipe_image),
                    cornerRadius = 0.dp,
                    padding = 0.dp,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = state.title,
                    textAlign = TextAlign.Center,
                    style = RecipesBookTheme.typography.headingXLarge,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = getStringValue(state.category.titleResId, state.category.title),
                    textAlign = TextAlign.Center,
                    style = RecipesBookTheme.typography.headingSmall,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = state.description,
                    style = RecipesBookTheme.typography.bodyLarge,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = stringResource(id = R.string.ingredients_label),
                    textAlign = TextAlign.Center,
                    style = RecipesBookTheme.typography.headingSmall,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = state.ingredients,
                    style = RecipesBookTheme.typography.bodyLarge,
                )
            }
        }
        RecipeDetailsTopAppBar(
            upPress = upPress,
            onEditRecipe = { onEditRecipe(state.id) },
            onShowShoppingList = onShowShoppingList,
        )

        if (state.dialog == Dialog.ShowShoppingListDialog) {
            ShoppingListDialog(
                state.shoppingListDropdown,
                onCreateShoppingList = {
                    onCreateShoppingList()
                },
                onUpdateShoppingList = { listId ->
                    onUpdateShoppingList(listId)
                },
                onHideShoppingList = onHideShoppingList,
            )
        } else {
            Unit
        }
    }
}

@Composable
private fun RecipeDetailsTopAppBar(
    upPress: () -> Unit,
    onEditRecipe: () -> Unit,
    onShowShoppingList: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            onAction = upPress,
            icon = Icons.Rounded.ArrowBack,
            iconContentResId = R.string.navigate_back
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButton(
            onAction = { onShowShoppingList() },
            icon = Icons.Rounded.ShoppingCart,
            iconContentResId = R.string.shopping_list_title
        )
        ActionButton(
            onAction = onEditRecipe,
            icon = Icons.Rounded.Edit,
            iconContentResId = R.string.button_edit
        )
    }
}

private const val CREATE_NEW_LIST = "Create new list"
private const val CHOOSE_LIST = "Choose list"

@Composable
private fun ShoppingListDialog(
    shoppingListDropdown: DropdownField,
    onCreateShoppingList: () -> Unit,
    onUpdateShoppingList: (Long) -> Unit,
    onHideShoppingList: () -> Unit,
) {
    val selectedShoppingList = remember { mutableStateOf(-1L) }
    AlertDialogComponent(
        title = stringResource(id = R.string.shopping_list_title),
        confirmButtonText = stringResource(id = R.string.ok_label),
        confirmButtonClicked = {
            onHideShoppingList()
            if (selectedShoppingList.value == -1L) onCreateShoppingList() else onUpdateShoppingList(
                selectedShoppingList.value
            )
        },
        dismissButtonText = stringResource(id = R.string.cancel_label),
        dismissButtonClicked = onHideShoppingList,
    ) {
        val options = listOf(CREATE_NEW_LIST, CHOOSE_LIST)
        val selectedOption = remember { mutableStateOf(options[0]) }

        if (shoppingListDropdown.spinnerItems.isEmpty()) {
            Text(
                text = stringResource(id = R.string.create_shopping_list_placeholder),
                style = RecipesBookTheme.typography.bodyLarge,
            )
        } else {
            Column {
                ListRadioButton(
                    isSelected = selectedOption.value == CREATE_NEW_LIST,
                    onClick = { selectedOption.value = CREATE_NEW_LIST },
                ) {
                    Text(
                        text = stringResource(id = R.string.create_shopping_list_placeholder),
                        style = RecipesBookTheme.typography.bodyLarge,
                    )
                }
                ListRadioButton(
                    isSelected = selectedOption.value == CHOOSE_LIST,
                    onClick = { selectedOption.value = CHOOSE_LIST },
                ) {
                    ListSpinner(
                        selectedShoppingList = selectedShoppingList.value,
                        spinnerItems = shoppingListDropdown.spinnerItems,
                        onListChosen = { selectedListId ->
                            selectedShoppingList.value = selectedListId
                            selectedOption.value = CHOOSE_LIST
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ListRadioButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onClick() },
            colors = radioButtonColors,
        )
        content()
    }
}

@Composable
private fun ListSpinner(
    selectedShoppingList: Long,
    spinnerItems: List<SpinnerItem>,
    onListChosen: (Long) -> Unit,
) {
    CardComponent(
        border = BorderStroke(1.dp, primaryLight),
        onClick = { onListChosen(selectedShoppingList) },
        elevation = 0.dp,
    ) {
        SpinnerComponent(
            label = stringResource(id = R.string.choose_shopping_list_placeholder),
            selectedItem = spinnerItems.firstOrNull { it.id == selectedShoppingList }?.value ?: "",
            spinnerItems = spinnerItems,
            onSelectionChanged = {
                onListChosen(it)
            },
        )
    }
}

@DevicePreviews
@Composable
private fun RecipeDetailsScreenPreview(
    @PreviewParameter(RecipeDetailsScreenStateProvider::class)
    screenState: RecipeDetailsScreenState
) {
    RecipesBookTheme {
        RecipeDetailsScreen(
            state = screenState,
            upPress = {},
            onEditRecipe = {},
            onShowShoppingList = {},
            onCreateShoppingList = {},
            onUpdateShoppingList = { },
            onHideShoppingList = {},
        )
    }
}