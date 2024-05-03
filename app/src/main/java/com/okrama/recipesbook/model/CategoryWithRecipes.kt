package com.okrama.recipesbook.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity

data class CategoryWithRecipes(
    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "categoryId",
        entity = Recipe::class,
        entityColumn = "recipeId",
        associateBy = Junction(
            value = CategoryAndRecipeEntity::class,
            parentColumn = "categoryId",
            entityColumn = "recipeId"
        )
    )
    var recipes: List<Recipe>
)
