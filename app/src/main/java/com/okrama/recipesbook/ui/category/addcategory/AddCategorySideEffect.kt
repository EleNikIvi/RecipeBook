package com.okrama.recipesbook.ui.category.addcategory

sealed interface AddCategorySideEffect {
    data class OnCategorySaved(val categoryId: Long) : AddCategorySideEffect
}