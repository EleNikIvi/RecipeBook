package com.okrama.recipesbook.ui.recipes

import android.os.Parcelable
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.model.CategoryListProvider.CATEGORY_ALL
import kotlinx.parcelize.Parcelize


data class RecipesScreenState(
    val recipes: List<Recipe> = emptyList(),
    val search: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category = CATEGORY_ALL,
    val isRefreshing: Boolean = false,
)

@Parcelize
data class RecipesPersistedState(
    val searchFieldEnabled: Boolean = false,
    val filterCategories: List<Category> = emptyList(),
    val selectedCategory: Category = CATEGORY_ALL,
) : Parcelable