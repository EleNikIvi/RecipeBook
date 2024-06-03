package com.okrama.recipesbook.domain.shoppinglist

import com.okrama.recipesbook.data.local.entity.Product
import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.model.ShoppingListWithProducts
import com.okrama.recipesbook.ui.shoppinglist.add.NewProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val INGREDIENTS_SEPARATOR = "\n"

class ShoppingListInteractor @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    fun getAllShoppingLists(): Flow<List<ShoppingList>> =
        shoppingListRepository.getAllShoppingLists()

    fun getAllShoppingListsWithProducts(): Flow<List<ShoppingListWithProducts>> =
        shoppingListRepository.getAllShoppingListsWithProducts()

    fun getShoppingListWithProducts(id: Long): Flow<ShoppingListWithProducts> =
        shoppingListRepository.getShoppingListWithProducts(listId = id)

    suspend fun addShoppingList(
        title: String,
        products: List<NewProduct>
    ): Long = shoppingListRepository.addShoppingList(
        shoppingList = ShoppingList(
            title = title,
        ),
        products = if (products.isEmpty()) emptyList() else products.map { it.name }
    )

    suspend fun updateShoppingList(
        listId: Long,
        title: String,
        products: List<NewProduct>
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

    suspend fun updateProductIsDone(product: Product, isDone: Boolean) {
        shoppingListRepository.updateProductIsDone(product.copy(isDone = isDone))
    }

    fun getProductsAsString(products: List<Product>) =
        products.joinToString(
            INGREDIENTS_SEPARATOR
        ) { it.product }
}