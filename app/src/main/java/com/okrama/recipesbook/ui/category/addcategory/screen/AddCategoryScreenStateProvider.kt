package com.okrama.recipesbook.ui.category.addcategory.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.category.addcategory.AddCategoryScreenState
import com.okrama.recipesbook.ui.core.LoremIpsum

class AddCategoryScreenStateProvider : PreviewParameterProvider<AddCategoryScreenState> {
    override val values: Sequence<AddCategoryScreenState> = sequenceOf(
        AddCategoryScreenState(
            imageUrl = "",
            title = "Title of new recipe",
            description = LoremIpsum.PARAGRAPH,
            canSave = true,
        )
    )
}