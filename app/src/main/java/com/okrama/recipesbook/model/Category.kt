package com.okrama.recipesbook.model

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String? = "",
    @StringRes val titleResId: Int? = 0,
)