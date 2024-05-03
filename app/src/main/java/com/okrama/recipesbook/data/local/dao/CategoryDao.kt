package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.okrama.recipesbook.model.CategoryWithRecipes
import com.okrama.recipesbook.domain.category.Categories
import com.okrama.recipesbook.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category ORDER BY categoryId ASC")
    fun getCategories(): Flow<Categories>

    @Query("SELECT * FROM category WHERE categoryId = :id")
    fun getCategory(id: Long): Flow<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCategory(category: Category): Long

    @Query("DELETE FROM category WHERE categoryId = :categoryId")
    suspend fun deleteCategory(categoryId: Long)

    @Transaction
    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun getCategoryWithRecipes(categoryId: Long): CategoryWithRecipes?

}