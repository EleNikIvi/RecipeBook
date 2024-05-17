package com.okrama.recipesbook.ui.details

import com.okrama.recipesbook.model.EMPTY_RECIPE_ID


data class RecipeDetailsScreenState(
    val id: Long = EMPTY_RECIPE_ID,
    val imageUrl: String = "",
    val title: String = "",
    val category: String = "",
    val description: String = "",
    val ingredients: String = "",
)