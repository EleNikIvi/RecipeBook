package com.okrama.recipesbook.domain.category

import com.okrama.recipesbook.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    fun getCategories(): Flow<Categories> = categoryRepository.getCategories()

    fun getCategory(id: Long): Flow<Category> = categoryRepository.getCategory(id = id)

    suspend fun addCategory(
        title: String,
    ): Long = categoryRepository.addCategory(
        category = Category(
            title = title,
        )
    )

    suspend fun updateCategory(
        id: Long,
        title: String,
    ) = categoryRepository.updateCategory(
        category = Category(
            categoryId = id,
            title = title,
        )
    )

    suspend fun deleteCategory(categoryId: Long) =
        categoryRepository.deleteCategory(categoryId = categoryId)
}