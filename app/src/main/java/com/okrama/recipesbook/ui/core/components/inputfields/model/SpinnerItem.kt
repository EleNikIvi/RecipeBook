package com.okrama.recipesbook.ui.core.components.inputfields.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class SpinnerItem(
    val id: Long,
    val value: String? = "",
    @StringRes val valueResId: Int? = null,
)

@Immutable
data class DropdownField(
    val value: String? = "",
    @StringRes val valueResId: Int? = null,
    val spinnerItems: ImmutableList<SpinnerItem> = persistentListOf(),
)
