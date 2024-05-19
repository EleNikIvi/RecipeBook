package com.okrama.recipesbook.data.repository

import com.okrama.recipesbook.base.DatabaseUtils
import com.okrama.recipesbook.base.DefaultDispatcher
import com.okrama.recipesbook.data.local.dao.CategoryAndRecipeDao
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.data.local.dao.RecipeAndIngredientsDao
import com.okrama.recipesbook.data.local.dao.RecipeDao
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity
import com.okrama.recipesbook.data.local.entity.Ingredient
import com.okrama.recipesbook.domain.recipe.RecipeRepository
import com.okrama.recipesbook.domain.recipe.RecipesWithIngredients
import com.okrama.recipesbook.model.CategoryId
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.model.RecipeWithIngredients
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val categoryAndRecipeDao: CategoryAndRecipeDao,
    private val recipeWithIngredientsDao: RecipeAndIngredientsDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    override fun getAllRecipesWithIngredients(): Flow<RecipesWithIngredients> =
        recipeWithIngredientsDao.getAllRecipesWithIngredients()

    override suspend fun getRecipesBy(categoryId: CategoryId): CategoryWithRecipes? =
        categoryDao.getCategoryWithRecipes(categoryId = categoryId)

    override fun getRecipeWithIngredients(id: Long): Flow<RecipeWithIngredients> =
        recipeWithIngredientsDao.getRecipeWithIngredients(id = id)

    override suspend fun addRecipe(
        recipe: Recipe,
        categoryId: CategoryId,
        ingredients: List<String>
    ) = insertOrUpdateRecipe(recipe = recipe, categoryId = categoryId, ingredients = ingredients)

    override suspend fun updateRecipe(
        recipe: Recipe,
        categoryId: CategoryId,
        ingredients: List<String>
    ) = insertOrUpdateRecipe(recipe = recipe, categoryId = categoryId, ingredients = ingredients)

    private suspend fun insertOrUpdateRecipe(
        recipe: Recipe,
        categoryId: CategoryId,
        ingredients: List<String>
    ): Long {
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
                DatabaseUtils.safeLaunch {
                    recipeWithIngredientsDao.deleteAllIngredientsFor(recipeId)
                    ingredients.forEach { ingredient ->
                        recipeWithIngredientsDao.insertOrUpdate(
                            Ingredient(
                                recipeId = recipeId,
                                ingredient = ingredient,
                            )
                        )
                    }
                }
            }
            recipeId
        }
    }

    override suspend fun deleteRecipe(recipeId: Long) = recipeDao.deleteRecipe(recipeId = recipeId)
}