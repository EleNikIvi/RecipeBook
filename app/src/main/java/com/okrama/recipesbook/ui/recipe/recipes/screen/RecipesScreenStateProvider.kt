package com.okrama.recipesbook.ui.recipe.recipes.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.LoremIpsum
import com.okrama.recipesbook.ui.recipe.recipes.RecipesScreenState

class RecipeScreenStateProvider : PreviewParameterProvider<RecipesScreenState> {
    override val values: Sequence<RecipesScreenState> = sequenceOf(
        RecipesScreenState(
            recipes = getPreviewRecipesList(),
        ),
        RecipesScreenState(),
        RecipesScreenState(
            isRefreshing = true,
        ),
    )
}

private fun getPreviewRecipesList() = listOf(
    Recipe(
        recipeId = 1,
        title = "Potato",
        description = LoremIpsum.LONG,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
    Recipe(
        recipeId = 2,
        title = LoremIpsum.LONG,
        description = "",
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
    Recipe(
        recipeId = 3,
        title = "",
        description = LoremIpsum.LONG,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
    Recipe(
        recipeId = 4,
        title = "recipe without image",
        description = LoremIpsum.LONG,
        imageUrl = "",
    ),
    Recipe(
        recipeId = 5,
        title = "turbio \uD83D\uDE00 \uD83D\uDE00 \uD83D\uDE00",
        description = LoremIpsum.LONG,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
)
