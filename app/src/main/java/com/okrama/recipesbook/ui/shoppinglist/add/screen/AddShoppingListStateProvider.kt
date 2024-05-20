package com.okrama.recipesbook.ui.shoppinglist.add.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.shoppinglist.add.AddShoppingListScreenState
import com.okrama.recipesbook.ui.shoppinglist.add.NewProduct

class AddShoppingListStateProvider : PreviewParameterProvider<AddShoppingListScreenState> {
    override val values: Sequence<AddShoppingListScreenState> = sequenceOf(
        AddShoppingListScreenState.Initial(
            title = "Title of new Shopping List",
            products = listOf(
                NewProduct("Sugar"),
                NewProduct("Flour"),
                NewProduct("Milk"),
                NewProduct("Eggs")
            ),
            canSave = true,
        )
    )
}