package com.okrama.recipesbook.ui.core.components.inputfields

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun getStringValue(@StringRes valueResId: Int?, value: String?) : String {
    return valueResId?.let { stringResource(id = it) } ?: value ?: ""
}