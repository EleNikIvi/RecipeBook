package com.okrama.recipesbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okrama.recipesbook.data.local.dao.CategoryAndRecipeDao
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.data.local.dao.RecipeAndIngredientsDao
import com.okrama.recipesbook.data.local.dao.RecipeDao
import com.okrama.recipesbook.data.local.dao.ShoppingListAndProductsDao
import com.okrama.recipesbook.data.local.dao.ShoppingListDao
import com.okrama.recipesbook.data.local.entity.CategoryAndRecipeEntity
import com.okrama.recipesbook.data.local.entity.Ingredient
import com.okrama.recipesbook.data.local.entity.Product
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.model.ShoppingList

@Database(
    entities = [
        Recipe::class,
        Category::class,
        CategoryAndRecipeEntity::class,
        Ingredient::class,
        ShoppingList::class,
        Product::class,
    ],
    version = 1,
)
abstract class RecipeBookDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val categoryDao: CategoryDao
    abstract val categoryAndRecipeDao: CategoryAndRecipeDao
    abstract val recipeAndIngredientsDao: RecipeAndIngredientsDao
    abstract val shoppingListDao: ShoppingListDao
    abstract val shoppingListAndProductsDao: ShoppingListAndProductsDao
}