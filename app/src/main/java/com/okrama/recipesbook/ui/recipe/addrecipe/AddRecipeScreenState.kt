package com.okrama.recipesbook.ui.recipe.addrecipe

import android.os.Parcelable
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.components.inputfields.model.DropdownField
import com.okrama.recipesbook.ui.core.components.inputfields.model.SpinnerItem
import com.okrama.recipesbook.ui.core.model.CategoryListProvider
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
    val selectedCategory: Category = CategoryListProvider.CATEGORY_ALL,
    val ingredients: String = "",
    val isChanged: Boolean = false,
) : Parcelable

object Categories {
    fun getCategoriesDropdown(
        categories: List<Category>,
        selectedCategory: Category? = null
    ): DropdownField {
        val spinnerItems = categories.map {
            SpinnerItem(
                id = it.categoryId,
                value = it.title ?: "",
                valueResId = it.titleResId,
            )
        }

        return DropdownField(
            value = selectedCategory?.title ?: "",
            valueResId = selectedCategory?.titleResId,
            spinnerItems = spinnerItems,
        )
    }
}