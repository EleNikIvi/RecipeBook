package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okrama.recipesbook.ui.core.Const.LOREM_IPSUM
import com.okrama.recipesbook.ui.core.components.ImageComponent
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.Grey0
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.recipes.RecipesScreenState

@Composable
fun RecipesLoadedContent(
    loadedState: RecipesScreenState.Loaded,
    onRecipeSelected: (Long) -> Unit,
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 130.dp),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
    ) {
        items(
            items = loadedState.recipes,
            key = { it.id },
        ) { recipe ->
            Column {
                RecipeItem(recipe, onRecipeSelected)
            }
        }
    }
}

@Composable
private fun RecipeItem(
    recipe: Recipe,
    onRecipeSelected: (Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onRecipeSelected(recipe.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Grey0
        )
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageComponent(
                imageUri = recipe.imageUrl,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                cornerRadius = 0.dp,
                padding = 0.dp,
                contentDescription = recipe.title,
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .semantics { contentDescription = recipe.title },
                text = recipe.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview
@Composable
private fun RecipeItemPreview() {
    RecipesBookTheme {
        RecipeItem(
            recipe = Recipe(
                id = 1L,
                title = LOREM_IPSUM,
                description = LOREM_IPSUM,
                imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
            ),
            onRecipeSelected = {},
        )
    }
}
