package com.okrama.recipesbook.ui.recipe.addrecipe

import android.os.Parcelable
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.components.inputfields.model.DropdownField
import com.okrama.recipesbook.ui.core.components.inputfields.model.SpinnerItem
import com.okrama.recipesbook.ui.core.model.CategoryUtil
import com.okrama.recipesbook.ui.core.model.CategoryUtil.getCategoryTitleResId
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

sealed interface AddRecipeScreenState {
    data class Initial(
        val imageUrl: String? = null,
        val title: String = "",
        val description: String = "",
        val categoriesDropdown: DropdownField = DropdownField(),
        val ingredients: String = "",
        val canSave: Boolean = false,
    ) : AddRecipeScreenState

    data object Saved : AddRecipeScreenState
}

@Parcelize
data class AddRecipePersistedState(
    val imageUrl: String? = null,
    val title: String = "",
    val description: String = "",
    val selectedCategory: Category = CategoryUtil.CATEGORY_ALL,
    val ingredients: String = "",
    val isChanged: Boolean = false,
) : Parcelable

object Categories {
    fun getCategoriesDropdown(
        categories: List<Category>,
        selectedCategory: Category? = null
    ): DropdownField {
        val spinnerItems = categories.map { category ->
            SpinnerItem(
                id = category.categoryId,
                value = category.title,
                valueResId = getCategoryTitleResId(category)
            )
        }

        return DropdownField(
            value = selectedCategory?.title ?: "",
            valueResId = selectedCategory?.let {
                getCategoryTitleResId(selectedCategory)
            },
            spinnerItems = spinnerItems.toImmutableList(),
        )
    }
}