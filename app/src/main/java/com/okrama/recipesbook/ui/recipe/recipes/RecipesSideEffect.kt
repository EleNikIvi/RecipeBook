package com.okrama.recipesbook.ui.recipe.recipes

sealed interface RecipesSideEffect {
    data object NavigateToAddRecipeScreen : RecipesSideEffect

    data class NavigateToEditRecipeScreen(
        val recipeId: Long
    ) : RecipesSideEffect

    data class NavigateToRecipeDetailsScreen(
        val recipeId: Long
    ) : RecipesSideEffect

    data object NavigateToAddCategoryScreen : RecipesSideEffect
}