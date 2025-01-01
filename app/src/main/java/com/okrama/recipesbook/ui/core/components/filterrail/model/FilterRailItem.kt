package com.okrama.recipesbook.ui.core.components.filterrail.model

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.okrama.recipesbook.ui.core.model.CategoryUtil
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class FilterRailItem(
    val id: Long,
    val value: String? = "",
    @StringRes val valueResId: Int? = null,
) : Parcelable

val FILTER_ALL = FilterRailItem(
    id = 0,
    value = "ALL",
    valueResId = CategoryUtil.CATEGORY_ALL_TITLE_RES_ID,
)