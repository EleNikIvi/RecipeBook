package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.CategoryId
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeInteractor @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    fun getAllRecipes(): Flow<Recipes> = recipeRepository.getAllRecipes()
    suspend fun getRecipesBy(categoryId: CategoryId): CategoryWithRecipes? =
        recipeRepository.getRecipesBy(categoryId = categoryId)

    fun getRecipe(id: Long): Flow<Recipe> = recipeRepository.getRecipe(id = id)

    suspend fun addRecipe(
        imageUrl: String,
        title: String,
        description: String,
        categoryId: CategoryId,
    ): Long = recipeRepository.addRecipe(
        recipe = Recipe(
            title = title,
            description = description,
            imageUrl = imageUrl,
        ),
        categoryId = categoryId,
    )

    suspend fun updateRecipe(
        id: Long,
        imageUrl: String,
        title: String,
        description: String,
        categoryId: CategoryId,
    ) = recipeRepository.updateRecipe(
        recipe = Recipe(
            recipeId = id,
            title = title,
            description = description,
            imageUrl = imageUrl,
        ),
        categoryId = categoryId,
    )

    suspend fun deleteRecipe(recipeId: Long) = recipeRepository.deleteRecipe(recipeId = recipeId)
}