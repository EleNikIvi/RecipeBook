package com.okrama.recipesbook.ui.details

import android.os.Parcelable
import com.okrama.recipesbook.model.EMPTY_RECIPE_ID
import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.ui.core.components.inputfields.model.DropdownField
import com.okrama.recipesbook.ui.core.components.inputfields.model.SpinnerItem
import kotlinx.parcelize.Parcelize


data class RecipeDetailsScreenState(
    val id: Long = EMPTY_RECIPE_ID,
    val imageUrl: String = "",
    val title: String = "",
    val category: String = "",
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
    val category: String = "",
    val description: String = "",
    val ingredients: String = "",
) : Parcelable

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
            spinnerItems = spinnerItems,
        )
    }
}