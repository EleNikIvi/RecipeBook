package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.CategoryId
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.model.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    fun getAllRecipesWithIngredients(): Flow<List<RecipeWithIngredients>>
    suspend fun getRecipesBy(categoryId: CategoryId): CategoryWithRecipes?
    fun getRecipeWithIngredients(id: Long): Flow<RecipeWithIngredients>
    suspend fun addRecipe(recipe: Recipe, categoryId: CategoryId, ingredients: List<String>): Long
    suspend fun updateRecipe(recipe: Recipe, categoryId: CategoryId, ingredients: List<String>): Long
    suspend fun deleteRecipe(recipeId: Long)
}