package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.ui.core.Const.LOREM_IPSUM
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.recipes.RecipesScreenState

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
        description = LOREM_IPSUM,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
    Recipe(
        recipeId = 2,
        title = LOREM_IPSUM,
        description = "",
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
    Recipe(
        recipeId = 3,
        title = "",
        description = LOREM_IPSUM,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
    Recipe(
        recipeId = 4,
        title = "recipe without image",
        description = LOREM_IPSUM,
        imageUrl = "",
    ),
    Recipe(
        recipeId = 5,
        title = "turbio \uD83D\uDE00 \uD83D\uDE00 \uD83D\uDE00",
        description = LOREM_IPSUM,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
    ),
)
