package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.CategoryId
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

typealias Recipes = List<Recipe>

interface RecipeRepository {
    fun getAllRecipes(): Flow<Recipes>
    suspend fun getRecipesBy(categoryId: CategoryId): CategoryWithRecipes?
    fun getRecipe(id: Long): Flow<Recipe>
    suspend fun addRecipe(recipe: Recipe, categoryId: CategoryId): Long
    suspend fun updateRecipe(recipe: Recipe, categoryId: CategoryId): Long
    suspend fun deleteRecipe(recipeId: Long)
}