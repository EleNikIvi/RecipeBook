package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity
import com.okrama.recipesbook.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryAndRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(categoryAndRecipeEntity: CategoryAndRecipeEntity)

    @Query("SELECT * FROM category_recipe WHERE recipeId = :id")
    fun getCategoryAndRecipe(id: Long): CategoryAndRecipeEntity
}