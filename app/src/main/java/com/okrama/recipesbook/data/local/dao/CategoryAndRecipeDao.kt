package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity

@Dao
interface CategoryAndRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(categoryAndRecipeEntity: CategoryAndRecipeEntity)
}