package com.okrama.recipesbook.ui.addrecipe.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.core.Const
import com.okrama.recipesbook.ui.addrecipe.AddRecipeScreenState

class AddRecipeScreenStateProvider : PreviewParameterProvider<AddRecipeScreenState> {
    override val values: Sequence<AddRecipeScreenState> = sequenceOf(
        AddRecipeScreenState.Initial(
            imageUrl = null,
            title = "Title of new recipe",
            description = Const.LOREM_IPSUM,
            canSave = true,
        )
    )
}