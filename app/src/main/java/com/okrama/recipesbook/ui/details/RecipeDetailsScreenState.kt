package com.okrama.recipesbook.ui.details

import com.okrama.recipesbook.model.EMPTY_RECIPE_ID


sealed interface RecipeDetailsScreenState {
    data class Initial(
        val id: Long = EMPTY_RECIPE_ID,
        val imageUrl: String = "",
        val title: String = "",
        val description: String = "",
    ) : RecipeDetailsScreenState

    object Updated : RecipeDetailsScreenState
    object Deleted : RecipeDetailsScreenState
}