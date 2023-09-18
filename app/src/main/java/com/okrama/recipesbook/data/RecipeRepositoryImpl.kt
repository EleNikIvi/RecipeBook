package com.okrama.recipesbook.data

import com.okrama.recipesbook.data.local.recipe.RecipeDao
import com.okrama.recipesbook.domain.recipe.RecipeRepository
import com.okrama.recipesbook.domain.recipe.Recipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
) : RecipeRepository {
    override fun getRecipes(): Flow<Recipes> = recipeDao.getAllRecipes()

    override suspend fun getRecipe(id: Long): Recipe = recipeDao.getRecipe(id = id)

    override suspend fun addRecipe(recipe: Recipe): Long = recipeDao.addRecipe(recipe = recipe)

    override suspend fun updateRecipe(recipe: Recipe) = recipeDao.updateRecipe(recipe = recipe)

    override suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe = recipe)
}