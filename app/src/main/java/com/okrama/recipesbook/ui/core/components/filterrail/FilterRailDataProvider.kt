package com.okrama.recipesbook.ui.core.components.filterrail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.model.Category

class FilterRailDataProvider : PreviewParameterProvider<List<Category>> {
    override val values: Sequence<List<Category>> = sequenceOf(
        listOf(
            Category(
                id = 1,
                title = "All",
            ),
            Category(
                id = 2,
                title = "Breakfast",
            ),
            Category(
                id = 3,
                title = "Lunch",
            )
        )
    )
}