package com.okrama.recipesbook.ui.shoppinglist.details

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Immutable
data class ShoppingListDetailsScreenState(
    val listTitle: String,
    val products: ImmutableList<ProductModel> = persistentListOf()
)

@Immutable
@Parcelize
data class ProductModel(
    val productId: Long = 0,
    val productName: String,
    val isDone: Boolean,
) : Parcelable