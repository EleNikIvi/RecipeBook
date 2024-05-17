package com.okrama.recipesbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okrama.recipesbook.data.local.dao.CategoryAndRecipeDao
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.data.local.dao.RecipeAndIngredientsDao
import com.okrama.recipesbook.data.local.dao.RecipeDao
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity
import com.okrama.recipesbook.data.local.entity.Ingredient
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe

@Database(
    entities = [Recipe::class, Category::class, CategoryAndRecipeEntity::class, Ingredient::class],
    version = 1,
)
abstract class RecipeBookDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val categoryDao: CategoryDao
    abstract val categoryAndRecipeDao: CategoryAndRecipeDao
    abstract val recipeAndIngredientsDao: RecipeAndIngredientsDao
}