package com.okrama.recipesbook.ui.shoppinglist.list

data class ShoppingListScreenState(
    val shoppingLists: List<ShoppingListModel> = emptyList(),
    val search: String = "",
    val isRefreshing: Boolean = false,
)

data class ShoppingListModel(
    val listId: Long,
    val listTitle: String,
    val products: String,
)