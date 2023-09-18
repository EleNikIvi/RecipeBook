package com.okrama.recipesbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okrama.recipesbook.data.local.recipe.RecipeDao
import com.okrama.recipesbook.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeBookDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}