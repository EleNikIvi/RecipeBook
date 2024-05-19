package com.okrama.recipesbook.ui.details

sealed interface RecipeDetailsSideEffect {
    data class NavigateToCreateShoppingListScreen(
        val recipeId: Long
    ): RecipeDetailsSideEffect

    data class NavigateToEditShoppingListScreen(
        val listId: Long,
        val recipeId: Long,
    ): RecipeDetailsSideEffect
}