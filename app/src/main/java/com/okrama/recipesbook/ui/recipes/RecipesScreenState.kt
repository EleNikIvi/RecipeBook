package com.okrama.recipesbook.ui.recipes

import com.okrama.recipesbook.model.Recipe

sealed class RecipesScreenState(val searchRequest: String = "") {
    object Initial : RecipesScreenState()

    data class Loaded(
        val recipes: List<Recipe>,
        val search: String = "",
    ) : RecipesScreenState(searchRequest = search)

    object Loading : RecipesScreenState()

    object Error : RecipesScreenState()
}