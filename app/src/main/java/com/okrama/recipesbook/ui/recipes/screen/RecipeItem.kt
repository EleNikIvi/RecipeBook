package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.LoremIpsum
import com.okrama.recipesbook.ui.core.components.ImageComponent
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.onTertiaryContainerLight
import com.okrama.recipesbook.ui.core.theme.tertiaryContainerLight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeItem(
    recipe: Recipe,
    onRecipeSelected: (Long) -> Unit,
    onDeleteRecipe: (Long) -> Unit,
    onEditRecipe: (Long) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onLongClick = {
                    isExpanded = true
                }, onClick = { onRecipeSelected(recipe.recipeId) }),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = tertiaryContainerLight
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
                color = onTertiaryContainerLight,
                text = recipe.title,
                style = RecipesBookTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            RecipesDropdownMenu(
                isExpanded = isExpanded,
                onDismiss = { isExpanded = false },
                onDeleteRecipe = {
                    isExpanded = false
                    onDeleteRecipe(recipe.recipeId)
                },
                onEditRecipe = {
                    isExpanded = false
                    onEditRecipe(recipe.recipeId)
                },
            )
        }
    }
}

@Composable
private fun RecipesDropdownMenu(
    isExpanded: Boolean,
    onDismiss: () -> Unit,
    onDeleteRecipe: () -> Unit,
    onEditRecipe: () -> Unit,
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.button_delete)) },
            onClick = {
                onDeleteRecipe()
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.button_edit)) },
            onClick = {
                onEditRecipe()
            }
        )
    }
}

@Preview
@Composable
private fun RecipeItemPreview() {
    RecipesBookTheme {
        RecipeItem(
            recipe = Recipe(
                recipeId = 1L,
                title = LoremIpsum.SHORT,
                description = LoremIpsum.LONG,
                imageUrl = "https://avatars.githubusercontent.com/u/1428207?v=4",
            ),
            onRecipeSelected = {},
            onDeleteRecipe = {},
            onEditRecipe = {},
        )
    }
}
