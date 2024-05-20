package com.okrama.recipesbook.data.repository

import com.okrama.recipesbook.base.DatabaseUtils
import com.okrama.recipesbook.base.DefaultDispatcher
import com.okrama.recipesbook.data.local.dao.ShoppingListAndProductsDao
import com.okrama.recipesbook.data.local.dao.ShoppingListDao
import com.okrama.recipesbook.data.local.entity.Product
import com.okrama.recipesbook.domain.shoppinglist.ShoppingListRepository
import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.model.ShoppingListWithProducts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListAndProductsDao: ShoppingListAndProductsDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ShoppingListRepository {
    override fun getAllShoppingLists(): Flow<List<ShoppingList>> =
        shoppingListDao.getAllShoppingLists()

    override fun getAllShoppingListsWithProducts(): Flow<List<ShoppingListWithProducts>> =
        shoppingListAndProductsDao.getAllShoppingListsWithProducts()

    override fun getShoppingListWithProducts(listId: Long): Flow<ShoppingListWithProducts> =
        shoppingListAndProductsDao.getShoppingListWithProducts(id = listId)

    override suspend fun addShoppingList(shoppingList: ShoppingList, products: List<String>): Long =
        insertOrUpdateShoppingList(shoppingList = shoppingList, products = products)

    override suspend fun updateShoppingList(
        shoppingList: ShoppingList,
        products: List<String>
    ): Long =
        insertOrUpdateShoppingList(shoppingList = shoppingList, products = products)

    override suspend fun updateProductIsDone(prduct: Product) {
        return withContext(defaultDispatcher) {
            DatabaseUtils.safeLaunch {
                shoppingListAndProductsDao.update(prduct)
            }
        }
    }

    private suspend fun insertOrUpdateShoppingList(
        shoppingList: ShoppingList,
        products: List<String>
    ): Long {
        return withContext(defaultDispatcher) {
            var shoppingListId = -1L
            DatabaseUtils.safeLaunch {
                shoppingListId =
                    shoppingListDao.insertOrUpdateShoppingList(shoppingList = shoppingList)
                DatabaseUtils.safeLaunch {
                    shoppingListAndProductsDao.deleteAllProductsFor(shoppingListId)
                    products.forEach { product ->
                        shoppingListAndProductsDao.insertOrUpdate(
                            Product(
                                shoppingListId = shoppingListId,
                                product = product,
                            )
                        )
                    }
                }
            }
            shoppingListId
        }
    }

    override suspend fun deleteShoppingList(listId: Long) =
        shoppingListDao.deleteShoppingList(listId = listId)
}