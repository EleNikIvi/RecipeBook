package com.okrama.recipesbook.data.repository

import com.okrama.recipesbook.data.local.DatabaseUtils
import com.okrama.recipesbook.base.DefaultDispatcher
import com.okrama.recipesbook.data.local.dao.CategoryAndRecipeDao
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.domain.category.Categories
import com.okrama.recipesbook.domain.category.CategoryRepository
import com.okrama.recipesbook.model.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryAndRecipeDao: CategoryAndRecipeDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override fun getCategories(): Flow<Categories> = categoryDao.getCategories()

    override fun getCategory(id: Long): Flow<Category> = categoryDao.getCategory(id = id)
    override suspend fun getCategoryForRecipe(recipeId: Long): Flow<Category> {
        var categoryId = 0L
        withContext(defaultDispatcher) {
            categoryId = categoryAndRecipeDao.getCategoryAndRecipe(recipeId).categoryId
        }
        return categoryDao.getCategory(id = categoryId)
    }

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