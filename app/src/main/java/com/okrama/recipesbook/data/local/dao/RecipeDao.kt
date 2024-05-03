package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.okrama.recipesbook.domain.recipe.Recipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe ORDER BY recipeId ASC")
    fun getAllRecipes(): Flow<Recipes>

    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    fun getRecipe(id: Long): Flow<Recipe>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateRecipe(recipe: Recipe): Long

    @Query("DELETE FROM recipe WHERE recipeId = :recipeId")
    suspend fun deleteRecipe(recipeId: Long)
}