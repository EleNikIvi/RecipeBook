package com.okrama.recipesbook.domain.shoppinglist

import com.okrama.recipesbook.model.RecipeWithIngredients
import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.model.ShoppingListWithProducts
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    fun getAllShoppingLists(): Flow<List<ShoppingList>>
    fun getShoppingListWithProducts(listId: Long): Flow<ShoppingListWithProducts>
    suspend fun addShoppingList(shoppingList: ShoppingList, products: List<String>): Long
    suspend fun updateShoppingList(shoppingList: ShoppingList, products: List<String>): Long
    suspend fun deleteShoppingList(listId: Long)
}