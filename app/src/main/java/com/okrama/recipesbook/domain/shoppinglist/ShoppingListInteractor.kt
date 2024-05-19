package com.okrama.recipesbook.domain.shoppinglist

import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.model.ShoppingListWithProducts
import com.okrama.recipesbook.ui.shoppinglist.add.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingListInteractor @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    fun getAllShoppingLists(): Flow<List<ShoppingList>> =
        shoppingListRepository.getAllShoppingLists()

    fun getShoppingListWithProducts(id: Long): Flow<ShoppingListWithProducts> =
        shoppingListRepository.getShoppingListWithProducts(listId = id)

    suspend fun addShoppingList(
        title: String,
        products: List<Product>
    ): Long = shoppingListRepository.addShoppingList(
        shoppingList = ShoppingList(
            title = title,
        ),
        products = if (products.isEmpty()) emptyList() else products.map { it.name }
    )

    suspend fun updateShoppingList(
        listId: Long,
        title: String,
        products: List<Product>
    ): Long {
        val recipeId = shoppingListRepository.updateShoppingList(
            shoppingList = ShoppingList(
                listId = listId,
                title = title,
            ),
            products = if (products.isEmpty()) emptyList() else products.map { it.name }
        )


        return recipeId
    }
}