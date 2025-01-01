package com.okrama.recipesbook.ui.core.model

import androidx.annotation.StringRes
import com.okrama.recipesbook.R
import com.okrama.recipesbook.model.Category

data class CategoryModel(
    val categoryId: Long = 0,
    val title: String,
    @StringRes val titleResId: Int? = null,
)

object CategoryUtil {
    val CATEGORY_ALL = Category(
        categoryId = 0,
        title = "ALL"
    )

    @StringRes
    val CATEGORY_ALL_TITLE_RES_ID = R.string.filter_category_all

    val CATEGORY_MODEL_ALL = CategoryModel(
        categoryId = 0,
        title = "ALL",
        titleResId = CATEGORY_ALL_TITLE_RES_ID,
    )

    fun getCategoryTitleResId(category: Category): Int? {
        return if (category.categoryId == 0L) CATEGORY_ALL_TITLE_RES_ID else null
    }

    fun getCategoryModel(
        category: Category,
    ): CategoryModel {
        return CategoryModel(
            categoryId = category.categoryId,
            title = category.title,
            titleResId = getCategoryTitleResId(category)
        )
    }
}