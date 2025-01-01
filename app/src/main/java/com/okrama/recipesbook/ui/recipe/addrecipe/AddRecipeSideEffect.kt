package com.okrama.recipesbook.ui.recipe.addrecipe

sealed interface AddRecipeSideEffect {
    data object NavigateToAddCategoryScreen : AddRecipeSideEffect
}