package com.okrama.recipesbook.data.local.entity

import androidx.room.Entity

@Entity(tableName = "category_recipe", primaryKeys = ["categoryId", "recipeId"])
data class CategoryAndRecipeEntity(
    val categoryId: Long,
    val recipeId: Long,
)
