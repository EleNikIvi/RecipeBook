package com.okrama.recipesbook.ui.recipe.details

import android.os.Parcelable
import androidx.annotation.StringRes
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.EMPTY_RECIPE_ID
import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.ui.core.components.inputfields.model.DropdownField
import com.okrama.recipesbook.ui.core.components.inputfields.model.SpinnerItem
import com.okrama.recipesbook.ui.core.model.CategoryModel
import com.okrama.recipesbook.ui.core.model.CategoryUtil.CATEGORY_ALL
import com.okrama.recipesbook.ui.core.model.CategoryUtil.CATEGORY_MODEL_ALL
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize


data class RecipeDetailsScreenState(
    val id: Long = EMPTY_RECIPE_ID,
    val imageUrl: String = "",
    val title: String = "",
    val category: CategoryModel = CATEGORY_MODEL_ALL,
    val description: String = "",
    val ingredients: String = "",
    val shoppingListDropdown: DropdownField = DropdownField(),
    val dialog: Dialog = Dialog.None
)

sealed interface Dialog {
    data object ShowShoppingListDialog : Dialog
    data object None : Dialog
}

@Parcelize
data class RecipeDetailsPersistedState(
    val imageUrl: String = "",
    val title: String = "",
    val category: Category = CATEGORY_ALL,
    val description: String = "",
    val ingredients: String = "",
) : Parcelable

fun checkAndGetCategory(category: Category): Category =
    if (category.categoryId == CATEGORY_ALL.categoryId) CATEGORY_ALL else category

object ShoppingLists {
    fun getShoppingListsDropdown(
        shoppingLists: List<ShoppingList>,
        selectedShoppingList: ShoppingList? = null
    ): DropdownField {
        val spinnerItems = shoppingLists.map {
            SpinnerItem(
                id = it.listId,
                value = it.title,
            )
        }

        return DropdownField(
            value = selectedShoppingList?.title ?: "",
            spinnerItems = spinnerItems.toImmutableList(),
        )
    }
}