package com.okrama.recipesbook.ui.core.components.filterrail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.core.components.filterrail.model.FilterRailItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class FilterRailPreviewDataProvider : PreviewParameterProvider<List<FilterRailItem>> {
    override val values: Sequence<ImmutableList<FilterRailItem>> = sequenceOf(
        listOf(
            FilterRailItem(
                id = 1,
                value = "All",
            ),
            FilterRailItem(
                id = 2,
                value = "Breakfast",
            ),
            FilterRailItem(
                id = 3,
                value = "Lunch",
            )
        ).toImmutableList()
    )
}