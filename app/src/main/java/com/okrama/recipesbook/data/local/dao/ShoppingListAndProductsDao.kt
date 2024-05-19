package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okrama.recipesbook.data.local.entity.Product
import com.okrama.recipesbook.model.ShoppingListWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListAndProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(product: Product)

    @Query("DELETE FROM product WHERE shoppingListId = :shoppingListId")
    fun deleteAllProductsFor(shoppingListId: Long)

    @Query("SELECT * FROM shopping_list WHERE listId = :id")
    fun getShoppingListWithProducts(id: Long): Flow<ShoppingListWithProducts>

    @Query("SELECT * FROM shopping_list")
    fun getAllShoppingListsWithProducts(): Flow<List<ShoppingListWithProducts>>
}