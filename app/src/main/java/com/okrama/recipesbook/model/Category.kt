package com.okrama.recipesbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imageUrl: String = "",
)