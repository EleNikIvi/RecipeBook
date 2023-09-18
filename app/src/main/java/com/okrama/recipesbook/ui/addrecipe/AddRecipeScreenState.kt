package com.okrama.recipesbook.ui.addrecipe

sealed interface AddRecipeScreenState {
    data class Initial(
        val imageUrl: String? = null,
        val title: String = "",
        val description: String = "",
        val canSave: Boolean = false,
    ) : AddRecipeScreenState

    object Saved : AddRecipeScreenState
}