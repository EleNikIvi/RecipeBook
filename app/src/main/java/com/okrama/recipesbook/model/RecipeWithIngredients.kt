package com.okrama.recipesbook.model

import androidx.room.Embedded
import androidx.room.Relation
import com.okrama.recipesbook.data.local.entity.Ingredient

data class RecipeWithIngredients(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )
    val ingredients: List<Ingredient>
)
