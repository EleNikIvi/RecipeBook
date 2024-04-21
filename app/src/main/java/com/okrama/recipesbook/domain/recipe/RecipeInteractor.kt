package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeInteractor @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    fun getRecipes(): Flow<Recipes> = recipeRepository.getRecipes()

    fun getRecipe(id: Long): Flow<Recipe> = recipeRepository.getRecipe(id = id)

    suspend fun addRecipe(
        imageUrl: String,
        title: String,
        description: String
    ): Long = recipeRepository.addRecipe(
        recipe = Recipe(
            title = title,
            description = description,
            imageUrl = imageUrl,
        )
    )

    suspend fun updateRecipe(
        id: Long,
        imageUrl: String,
        title: String,
        description: String
    ) = recipeRepository.updateRecipe(
        recipe = Recipe(
            id = id,
            title = title,
            description = description,
            imageUrl = imageUrl,
        )
    )

    suspend fun deleteRecipe(recipeId: Long) = recipeRepository.deleteRecipe(recipeId = recipeId)
}