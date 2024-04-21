package com.okrama.recipesbook.data.local.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.okrama.recipesbook.domain.recipe.Recipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe ORDER BY id ASC")
    fun getAllRecipes(): Flow<Recipes>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipe(id: Long): Flow<Recipe>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateRecipe(recipe: Recipe): Long

    @Query("DELETE FROM recipe WHERE id = :recipeId")
    suspend fun deleteRecipe(recipeId: Long)
}