package com.okrama.recipesbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okrama.recipesbook.model.ShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list ORDER BY listId ASC")
    fun getAllShoppingLists(): Flow<List<ShoppingList>>

    @Query("SELECT * FROM shopping_list WHERE listId = :id")
    fun getShoppingList(id: Long): Flow<ShoppingList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateShoppingList(shoppingList: ShoppingList): Long

    @Query("DELETE FROM shopping_list WHERE listId = :listId")
    suspend fun deleteShoppingList(listId: Long)
}