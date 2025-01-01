package com.okrama.recipesbook.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val EMPTY_CATEGORY_ID = -1L
typealias CategoryId = Long

@Parcelize
@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: CategoryId = 0,
    val title: String,
) : Parcelable