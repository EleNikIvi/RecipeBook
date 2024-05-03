package com.okrama.recipesbook.ui.core.model

import com.okrama.recipesbook.R
import com.okrama.recipesbook.model.Category

object CategoryListProvider {
    val CATEGORY_ALL = Category(
        categoryId = 0,
        titleResId = R.string.filter_category_all,
    )

    fun getCategories(categories: List<Category>): List<Category> {
        return mutableListOf<Category>(CATEGORY_ALL).also { it.addAll(categories.subList(1, categories.size)) }
    }
}