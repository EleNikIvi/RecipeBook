package com.okrama.recipesbook.ui.addcategory


const val MAX_CATEGORY_TITLE_CHAR = 10
sealed interface AddCategoryScreenState {
    data class Initial(
        val imageUrl: String? = null,
        val title: String = "",
        val description: String = "",
        val canSave: Boolean = false,
    ) : AddCategoryScreenState

    object Saved : AddCategoryScreenState
}