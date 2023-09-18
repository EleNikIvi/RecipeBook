package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.recipes.RecipesScreenState

const val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

class RecipeScreenStateProvider: PreviewParameterProvider<RecipesScreenState> {
    override val values: Sequence<RecipesScreenState> = sequenceOf(
        RecipesScreenState.Loaded(
            recipes = getPreviewRecipesList(),
        ),
        RecipesScreenState.Initial,
        RecipesScreenState.Loading,
        RecipesScreenState.Error,
    )
}

private fun getPreviewRecipesList() = listOf(
    Recipe(
        id = 1,
        title = "Potato",
        description = LOREM_IPSUM,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
        linkUrl = "https://www.facebook.com/reel/1310238029858604?mibextid=9drbnH",
    ),
    Recipe(
        id = 2,
        title = LOREM_IPSUM,
        description = "",
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
        linkUrl = "",
    ),
    Recipe(
        id = 3,
        title = "",
        description = LOREM_IPSUM,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
        linkUrl = "https://www.facebook.com/reel/1310238029858604?mibextid=9drbnH",
    ),
    Recipe(
        id = 4,
        title = "recipe without image",
        description = LOREM_IPSUM,
        imageUrl = "",
        linkUrl = "",
    ),
    Recipe(
        id = 5,
        title = "turbio \uD83D\uDE00 \uD83D\uDE00 \uD83D\uDE00",
        description = LOREM_IPSUM,
        imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
        linkUrl = "",
    ),
)
