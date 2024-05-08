package com.okrama.recipesbook.domain.category

import com.okrama.recipesbook.model.Category
import kotlinx.coroutines.flow.Flow

typealias Categories = List<Category>

interface CategoryRepository {
    fun getCategories(): Flow<Categories>
    fun getCategory(id: Long): Flow<Category>
    suspend fun getCategoryForRecipe(recipeId: Long): Flow<Category>
    suspend fun addCategory(category: Category): Long
    suspend fun updateCategory(category: Category): Long
    suspend fun deleteCategory(categoryId: Long)
}