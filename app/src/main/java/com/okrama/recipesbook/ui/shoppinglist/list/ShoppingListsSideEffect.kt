package com.okrama.recipesbook.ui.shoppinglist.list

sealed interface ShoppingListsSideEffect {
    data object NavigateToCreateShoppingListScreen : ShoppingListsSideEffect
    data class NavigateToShoppingListDetailsScreen(
        val listId: Long,
    ) : ShoppingListsSideEffect
}