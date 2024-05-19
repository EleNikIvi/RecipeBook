package com.okrama.recipesbook.ui.details.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.core.LoremIpsum
import com.okrama.recipesbook.ui.details.RecipeDetailsScreenState

class RecipeDetailsScreenStateProvider : PreviewParameterProvider<RecipeDetailsScreenState> {
    override val values: Sequence<RecipeDetailsScreenState> = sequenceOf(
        RecipeDetailsScreenState(
            imageUrl = "",
            title = "Title of new recipe",
            description = LoremIpsum.PARAGRAPH,
        )
    )
}