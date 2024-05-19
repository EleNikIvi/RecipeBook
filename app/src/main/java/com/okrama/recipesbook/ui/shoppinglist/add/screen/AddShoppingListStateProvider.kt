package com.okrama.recipesbook.ui.shoppinglist.add.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.shoppinglist.add.AddShoppingListState
import com.okrama.recipesbook.ui.shoppinglist.add.Product

class AddShoppingListStateProvider : PreviewParameterProvider<AddShoppingListState> {
    override val values: Sequence<AddShoppingListState> = sequenceOf(
        AddShoppingListState.Initial(
            title = "Title of new Shopping List",
            products = listOf(
                Product("Sugar"),
                Product("Flour"),
                Product("Milk"),
                Product("Eggs")
            ),
            canSave = true,
        )
    )
}