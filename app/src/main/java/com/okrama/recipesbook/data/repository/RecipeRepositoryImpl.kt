package com.okrama.recipesbook.data.repository

import com.okrama.recipesbook.base.DatabaseUtils
import com.okrama.recipesbook.base.DefaultDispatcher
import com.okrama.recipesbook.data.local.dao.CategoryAndRecipeDao
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.data.local.dao.RecipeDao
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity
import com.okrama.recipesbook.domain.recipe.RecipeRepository
import com.okrama.recipesbook.domain.recipe.Recipes
import com.okrama.recipesbook.model.CategoryId
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val categoryAndRecipeDao: CategoryAndRecipeDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : RecipeRepository {
    override fun getAllRecipes(): Flow<Recipes> = recipeDao.getAllRecipes()
    override suspend fun getRecipesBy(categoryId: CategoryId): CategoryWithRecipes? =
        categoryDao.getCategoryWithRecipes(categoryId = categoryId)

    override fun getRecipe(id: Long): Flow<Recipe> = recipeDao.getRecipe(id = id)

    override suspend fun addRecipe(recipe: Recipe, categoryId: CategoryId) =
        insertOrUpdateRecipe(recipe = recipe, categoryId = categoryId)
    override suspend fun updateRecipe(recipe: Recipe, categoryId: CategoryId) =
        insertOrUpdateRecipe(recipe = recipe, categoryId = categoryId)

    private suspend fun insertOrUpdateRecipe(recipe: Recipe, categoryId: CategoryId): Long {
        return withContext(defaultDispatcher) {
            var recipeId = -1L
            DatabaseUtils.safeLaunch {
                recipeId = recipeDao.insertOrUpdateRecipe(recipe = recipe)
                DatabaseUtils.safeLaunch {
                    categoryAndRecipeDao.insertOrUpdate(
                        CategoryAndRecipeEntity(
                            categoryId = categoryId,
                            recipeId = recipeId
                        )
                    )
                }
            }
            recipeId
        }
    }

    override suspend fun deleteRecipe(recipeId: Long) = recipeDao.deleteRecipe(recipeId = recipeId)
}