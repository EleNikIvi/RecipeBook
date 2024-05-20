package com.okrama.recipesbook.ui.shoppinglist.add.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.button.ButtonAdd
import com.okrama.recipesbook.ui.core.components.CardComponent
import com.okrama.recipesbook.ui.core.components.SmallTopAppBarScreenContainer
import com.okrama.recipesbook.ui.core.components.inputfields.RecipeTextField
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.primaryLight
import com.okrama.recipesbook.ui.shoppinglist.add.AddShoppingListScreenState

@Composable
fun AddShoppingListScreen(
    state: AddShoppingListScreenState,
    onListNameChange: (String) -> Unit,
    onProductChange: (Int, String) -> Unit,
    onDeleteProduct: (Int) -> Unit,
    onAddNewProduct: () -> Unit,
    onSaveList: () -> Unit,
    upPress: () -> Unit,
) {
    if (state is AddShoppingListScreenState.Initial) {
        SmallTopAppBarScreenContainer(
            title = stringResource(id = R.string.edit_shopping_list_title),
            upPress = upPress,
            onAction = onSaveList,
            actionButtonEnabled = state.canSave,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            RecipeTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = state.title,
                onTextChange = onListNameChange,
                placeholder = stringResource(id = R.string.shopping_list_name_placeholder),
                singleLine = true,
            )

            state.products.forEachIndexed { index, product ->
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RecipeTextField(
                        modifier = Modifier.weight(1f),
                        text = product.name,
                        onTextChange = { name -> onProductChange(index, name) },
                        placeholder = stringResource(id = R.string.shopping_list_product_placeholder),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CardComponent(
                        onClick = { onDeleteProduct(index) },
                        contentColor = primaryLight,
                        elevation = RecipesBookTheme.elevation.medium,
                    ) {
                        Column {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.TwoTone.Delete),
                                modifier = Modifier
                                    .widthIn(min = 36.dp)
                                    .heightIn(min = 36.dp),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
            ButtonAdd(
                modifier = Modifier.padding(8.dp),
                onClick = onAddNewProduct,
                largeAppearance = true,
            )
        }
    } else if (state is AddShoppingListScreenState.Saved) {
        LaunchedEffect(Unit) {
            upPress()
        }
    }
}

@DevicePreviews
@Composable
private fun AddCategoryScreenPreview(
    @PreviewParameter(AddShoppingListStateProvider::class)
    screenState: AddShoppingListScreenState
) {
    RecipesBookTheme {
        AddShoppingListScreen(
            state = screenState,
            onListNameChange = {},
            onProductChange = {_, _ -> },
            onDeleteProduct = {},
            onAddNewProduct = {},
            onSaveList = {},
            upPress = {},
        )
    }
}