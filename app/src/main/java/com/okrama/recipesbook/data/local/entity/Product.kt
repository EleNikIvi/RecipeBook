package com.okrama.recipesbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    val shoppingListId: Long,
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    val product: String,
    val isDone: Boolean = false,
)
