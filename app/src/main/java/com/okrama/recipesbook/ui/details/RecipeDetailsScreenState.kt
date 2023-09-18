package com.okrama.recipesbook.ui.details


sealed interface RecipeDetailsScreenState {
    data class Initial(
        val imageUrl: String = "",
        val title: String = "",
        val description: String = "",
    ) : RecipeDetailsScreenState

    object Updated : RecipeDetailsScreenState
    object Deleted : RecipeDetailsScreenState
}