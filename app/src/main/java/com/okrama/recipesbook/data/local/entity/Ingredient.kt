package com.okrama.recipesbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class Ingredient(
    val recipeId: Long,
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Long = 0,
    val ingredient: String,
)
