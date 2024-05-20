package com.okrama.recipesbook.ui.shoppinglist.add

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface AddShoppingListScreenState {
    data class Initial(
        val title: String = "",
        val products: List<NewProduct> = emptyList(),
        val canSave: Boolean = false,
    ) : AddShoppingListScreenState

    data object Saved : AddShoppingListScreenState
}

@Parcelize
data class AddShoppingListPersistedState(
    val title: String = "",
    val products: List<NewProduct> = emptyList(),
    val isChanged: Boolean = false,
) : Parcelable

@Parcelize
data class NewProduct(
    val name: String = "",
) : Parcelable