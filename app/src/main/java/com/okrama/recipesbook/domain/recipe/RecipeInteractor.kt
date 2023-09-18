package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeInteractor @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    fun getRecipes(): Flow<Recipes> = recipeRepository.getRecipes()

    suspend fun getRecipe(id: Long): Recipe = recipeRepository.getRecipe(id = id)

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

    suspend fun updateRecipe(recipe: Recipe) = recipeRepository.updateRecipe(recipe = recipe)

    suspend fun deleteRecipe(recipe: Recipe) = recipeRepository.deleteRecipe(recipe = recipe)
}