package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

typealias Recipes = List<Recipe>

interface RecipeRepository {
    fun getRecipes(): Flow<Recipes>
    fun getRecipe(id: Long): Flow<Recipe>
    suspend fun addRecipe(recipe: Recipe): Long
    suspend fun updateRecipe(recipe: Recipe): Long
    suspend fun deleteRecipe(recipeId: Long)
}