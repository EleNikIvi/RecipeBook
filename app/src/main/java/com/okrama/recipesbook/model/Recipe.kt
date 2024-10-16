package com.okrama.recipesbook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey

const val EMPTY_RECIPE_ID = -1L

@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0,
    val title: String,
    val description: String,
    val imageUrl: String = "",
) : Parcelable
