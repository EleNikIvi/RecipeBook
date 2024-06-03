package com.okrama.recipesbook.ui.category.addcategory


const val MAX_CATEGORY_TITLE_CHAR = 10

data class AddCategoryScreenState(
    val imageUrl: String? = null,
    val title: String = "",
    val description: String = "",
    val canSave: Boolean = false,
)