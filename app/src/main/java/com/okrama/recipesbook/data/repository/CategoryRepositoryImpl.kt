package com.okrama.recipesbook.data.repository

import com.okrama.recipesbook.base.DatabaseUtils
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.domain.category.Categories
import com.okrama.recipesbook.domain.category.CategoryRepository
import com.okrama.recipesbook.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun getCategories(): Flow<Categories> = categoryDao.getCategories()

    override fun getCategory(id: Long): Flow<Category> = categoryDao.getCategory(id = id)

    override suspend fun addCategory(category: Category): Long =
        insertOrUpdateCategory(category = category)

    override suspend fun updateCategory(category: Category): Long =
        insertOrUpdateCategory(category = category)

    private suspend fun insertOrUpdateCategory(category: Category): Long {
        var categoryId = -1L
        DatabaseUtils.safeLaunch {
            categoryId = categoryDao.insertOrUpdateCategory(category = category)
        }
        return categoryId
    }

    override suspend fun deleteCategory(categoryId: Long) =
        categoryDao.deleteCategory(categoryId = categoryId)
}