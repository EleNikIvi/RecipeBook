package com.okrama.recipesbook.ui.core.components.inputfields.model

import androidx.annotation.StringRes

data class SpinnerItem(
    val id: Long,
    val value: String? = "",
    @StringRes var valueResId: Int? = null,
)
