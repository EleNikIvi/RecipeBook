package com.okrama.recipesbook.ui.core.components.inputfields.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

data class SpinnerItem(
    val id: Long,
    val value: String? = "",
    @StringRes var valueResId: Int? = null,
)

@Immutable
data class DropdownField(
    val value: String? = "",
    @StringRes var valueResId: Int? = null,
    val spinnerItems: List<SpinnerItem> = emptyList(),
)
