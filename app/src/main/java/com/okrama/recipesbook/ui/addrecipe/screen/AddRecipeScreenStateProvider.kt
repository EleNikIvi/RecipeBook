package com.okrama.recipesbook.ui.addrecipe.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.addrecipe.AddRecipeScreenState
import com.okrama.recipesbook.ui.core.LoremIpsum

class AddRecipeScreenStateProvider : PreviewParameterProvider<AddRecipeScreenState> {
    override val values: Sequence<AddRecipeScreenState> = sequenceOf(
        AddRecipeScreenState.Initial(
            imageUrl = null,
            title = "Title of new recipe",
            description = LoremIpsum.PARAGRAPH,
            canSave = true,
        )
    )
}