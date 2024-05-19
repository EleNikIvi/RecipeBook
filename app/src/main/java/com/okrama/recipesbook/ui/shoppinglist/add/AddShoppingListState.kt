package com.okrama.recipesbook.ui.shoppinglist.add

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface AddShoppingListState {
    data class Initial(
        val title: String = "",
        val products: List<Product> = emptyList(),
        val canSave: Boolean = false,
    ) : AddShoppingListState

    data object Saved : AddShoppingListState
}

@Parcelize
data class AddShoppingListPersistedState(
    val title: String = "",
    val products: List<Product> = emptyList(),
    val isChanged: Boolean = false,
) : Parcelable

@Parcelize
data class Product(
    val name: String = "",
) : Parcelable