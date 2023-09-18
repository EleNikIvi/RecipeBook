package com.okrama.recipesbook.data.local.recipe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.okrama.recipesbook.domain.recipe.Recipes
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe ORDER BY id ASC")
    fun getAllRecipes(): Flow<Recipes>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipe(id: Long): Recipe

    @Insert(onConflict = IGNORE)
    suspend fun addRecipe(recipe: Recipe): Long

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}