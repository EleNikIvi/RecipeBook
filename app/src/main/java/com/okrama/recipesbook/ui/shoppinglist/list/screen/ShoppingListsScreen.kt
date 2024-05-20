package com.okrama.recipesbook.ui.shoppinglist.list.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.topappbar.LargeTopAppBar
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.backgroundLight
import com.okrama.recipesbook.ui.core.theme.inversePrimaryLight
import com.okrama.recipesbook.ui.core.theme.primaryLight
import com.okrama.recipesbook.ui.shoppinglist.list.ShoppingListScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListsScreen(
    screenState: ShoppingListScreenState,
    onAddShoppingList: () -> Unit,
    onShoppingListDetails: (Long) -> Unit,
    onSearchFieldClear: () -> Unit,
    onSearchTermChange: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = backgroundLight,
        topBar = {
            LargeTopAppBar(
                search = screenState.search,
                title = stringResource(id = R.string.shopping_list_title),
                isCollapsed = false,
                searchPlaceholder = stringResource(id = R.string.search_shopping_list_hint),
                onAction = onAddShoppingList,
                onSearchTermChange = onSearchTermChange,
                onSearchFieldClear = onSearchFieldClear,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(items = screenState.shoppingLists, key = { it.listId }) { shoppingList ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onShoppingListDetails(shoppingList.listId) },
                    border = BorderStroke(1.dp, primaryLight),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = RecipesBookTheme.elevation.small
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = inversePrimaryLight
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = shoppingList.listTitle,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = RecipesBookTheme.typography.bodyLargeStrong,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = shoppingList.products,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            style = RecipesBookTheme.typography.bodyLarge,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@DevicePreviews
@Composable
private fun ShoppingListsScreenPreview(
    @PreviewParameter(ShoppingListsScreenStateProvider::class)
    screenState: ShoppingListScreenState
) {
    RecipesBookTheme {
        ShoppingListsScreen(
            screenState = screenState,
            onAddShoppingList = {},
            onShoppingListDetails = {},
            onSearchFieldClear = {},
            onSearchTermChange = {},
        )
    }
}