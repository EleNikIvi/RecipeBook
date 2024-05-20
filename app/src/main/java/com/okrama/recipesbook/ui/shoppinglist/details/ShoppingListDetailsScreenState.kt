package com.okrama.recipesbook.ui.shoppinglist.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ShoppingListDetailsScreenState(
    val listTitle: String = "",
    val products: List<ProductModel> = emptyList()
)

@Parcelize
data class ProductModel(
    val productId: Long = 0,
    val productName: String,
    val isDone: Boolean,
) : Parcelable