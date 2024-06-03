package com.okrama.recipesbook.model

import androidx.room.Embedded
import androidx.room.Relation
import com.okrama.recipesbook.data.local.entity.Product

data class ShoppingListWithProducts(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "shoppingListId"
    )
    val products: List<Product>
)

