package com.okrama.recipesbook.ui.shoppinglist.list

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Immutable
data class ShoppingListScreenState(
    val shoppingLists: ImmutableList<ShoppingListModel> = persistentListOf(),
    val search: String = "",
    val isRefreshing: Boolean = false,
)

@Immutable
@Parcelize
data class ShoppingListModel(
    val listId: Long,
    val listTitle: String,
    val products: String,
) : Parcelable