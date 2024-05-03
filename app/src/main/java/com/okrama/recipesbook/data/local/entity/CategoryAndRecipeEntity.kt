package com.okrama.recipesbook.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["categoryId", "recipeId"])
data class CategoryAndRecipeEntity(
    val categoryId: Long,
    val recipeId: Long,
)
