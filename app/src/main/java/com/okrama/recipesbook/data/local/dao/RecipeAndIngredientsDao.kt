package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okrama.recipesbook.data.local.entity.Ingredient
import com.okrama.recipesbook.model.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeAndIngredientsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(ingredient: Ingredient)

    @Query("DELETE FROM ingredient WHERE recipeId = :recipeId")
    fun deleteAllIngredientsFor(recipeId: Long)

    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    fun getRecipeWithIngredients(id: Long): Flow<RecipeWithIngredients>

    @Query("SELECT * FROM recipe")
    fun getAllRecipesWithIngredients(): Flow<List<RecipeWithIngredients>>
}