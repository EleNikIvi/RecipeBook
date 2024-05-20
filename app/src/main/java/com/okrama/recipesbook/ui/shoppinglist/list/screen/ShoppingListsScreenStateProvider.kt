package com.okrama.recipesbook.ui.shoppinglist.list.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.core.LoremIpsum
import com.okrama.recipesbook.ui.shoppinglist.list.ShoppingListModel
import com.okrama.recipesbook.ui.shoppinglist.list.ShoppingListScreenState

class ShoppingListsScreenStateProvider : PreviewParameterProvider<ShoppingListScreenState> {
    override val values: Sequence<ShoppingListScreenState> = sequenceOf(
        ShoppingListScreenState(
            shoppingLists = getPreviewList(),
        ),
        ShoppingListScreenState(
            search = "First"
        ),
        ShoppingListScreenState(
            isRefreshing = true,
        ),
    )
}

private fun getPreviewList() = listOf(
    ShoppingListModel(
        listId = 1,
        listTitle = "First shopping list",
        products = "Sugar\n${LoremIpsum.LONG}\nMilk\nFlour\nSomething sweet",
    ),
    ShoppingListModel(
        listId = 2,
        listTitle = "Second shopping list",
        products = "Sugar\n${LoremIpsum.LONG}"
    ),
    ShoppingListModel(
        listId = 3,
        listTitle = "Third shopping list",
        products = "",
    ),
    ShoppingListModel(
        listId = 4,
        listTitle = LoremIpsum.LONG,
        products = "${LoremIpsum.LONG}\n${LoremIpsum.LONG}",
    ),
)