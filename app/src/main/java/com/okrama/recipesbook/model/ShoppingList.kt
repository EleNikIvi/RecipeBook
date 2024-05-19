package com.okrama.recipesbook.model

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val EMPTY_LIST_ID = -1L
@Parcelize
@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val listId: Long = 0,
    val title: String = "",
) : Parcelable
