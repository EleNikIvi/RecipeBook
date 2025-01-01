package com.okrama.recipesbook.ui.recipe.recipes

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.components.filterrail.model.FILTER_ALL
import com.okrama.recipesbook.ui.core.components.filterrail.model.FilterRailItem
import com.okrama.recipesbook.ui.core.model.CategoryUtil
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

@Immutable
data class RecipesScreenState(
    val recipes: List<Recipe> = emptyList(),
    val search: String = "",
    val categories: ImmutableList<FilterRailItem> = persistentListOf(),
    val selectedCategory: FilterRailItem = FilterRailItem(
        id = FILTER_ALL.id,
        valueResId = FILTER_ALL.valueResId
    ),
    val isRefreshing: Boolean = false,
)

@Parcelize
data class RecipesPersistedState(
    val searchFieldEnabled: Boolean = false,
    val filterCategories: List<FilterRailItem> = emptyList(),
    val selectedCategory: FilterRailItem = FILTER_ALL,
) : Parcelable

object CategoriesFilterRail {
    fun getCategoriesFilterRail(
        categories: List<Category>,
    ): ImmutableList<FilterRailItem> {
        return categories.map { category ->
            getCategoryFilterRail(category)
        }.toImmutableList()
    }

    fun getCategoryFilterRail(
        category: Category,
    ): FilterRailItem {
        return FilterRailItem(
            id = category.categoryId,
            value = category.title,
            valueResId = CategoryUtil.getCategoryTitleResId(category)
        )
    }
}