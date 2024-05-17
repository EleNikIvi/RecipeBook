package com.okrama.recipesbook.domain.recipe

import com.okrama.recipesbook.data.local.entity.Ingredient
import com.okrama.recipesbook.model.CategoryId
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.model.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val INGREDIENTS_SEPARATOR = "\n"

class RecipeInteractor @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    fun getAllRecipes(): Flow<Recipes> = recipeRepository.getAllRecipes()
    suspend fun getRecipesBy(categoryId: CategoryId): CategoryWithRecipes? =
        recipeRepository.getRecipesBy(categoryId = categoryId)

    fun getRecipeWithIngredients(id: Long): Flow<RecipeWithIngredients> =
        recipeRepository.getRecipeWithIngredients(id = id)

    suspend fun addRecipe(
        imageUrl: String,
        title: String,
        description: String,
        categoryId: CategoryId,
        ingredients: String
    ): Long = recipeRepository.addRecipe(
        recipe = Recipe(
            title = title,
            description = description,
            imageUrl = imageUrl,
        ),
        categoryId = categoryId,
        ingredients = getIngredientsAsList(ingredients),
    )

    suspend fun updateRecipe(
        id: Long,
        imageUrl: String,
        title: String,
        description: String,
        categoryId: CategoryId,
        ingredients: String,
    ): Long {
        val recipeId = recipeRepository.updateRecipe(
            recipe = Recipe(
                recipeId = id,
                title = title,
                description = description,
                imageUrl = imageUrl,
            ),
            categoryId = categoryId,
            ingredients = getIngredientsAsList(ingredients),
        )


        return recipeId
    }

    private fun getIngredientsAsList(ingredients: String): List<String> =
        ingredients.lines()

    fun getIngredientsAsString(ingredients: List<Ingredient>) =
        ingredients.joinToString(
            INGREDIENTS_SEPARATOR
        ) { it.ingredient }

    suspend fun deleteRecipe(recipeId: Long) = recipeRepository.deleteRecipe(recipeId = recipeId)
}