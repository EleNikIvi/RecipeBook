package com.okrama.recipesbook.ui.recipes

import com.okrama.recipesbook.R
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe

private val CATEGORY_ALL = Category(
    id = 0,
    titleResId = R.string.filter_category_all,
)

data class RecipesScreenState(
    val recipes: List<Recipe> = emptyList(),
    val search: String = "",
    val filterCategories: List<Category> = mutableListOf(CATEGORY_ALL),
    val selectedCategory: Category = CATEGORY_ALL,
    val isRefreshing: Boolean = false,
)