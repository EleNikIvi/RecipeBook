package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

typealias Recipes = List<Recipe>

interface RecipeRepository {
    fun getRecipes(): Flow<Recipes>
    suspend fun getRecipe(id: Long): Recipe
    suspend fun addRecipe(recipe: Recipe): Long
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
}