package com.okrama.recipesbook.ui.shoppinglist.details

sealed interface ShoppingListDetailsSideEffect {
    data class NavigateToEditShoppingListScreen(
        val listId: Long,
    ) : ShoppingListDetailsSideEffect
}