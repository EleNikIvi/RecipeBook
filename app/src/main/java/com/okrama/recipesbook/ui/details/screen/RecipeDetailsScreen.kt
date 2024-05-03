package com.okrama.recipesbook.ui.details.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.components.ImageComponent
import com.okrama.recipesbook.ui.core.theme.Grey0
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.Yellow0
import com.okrama.recipesbook.ui.core.theme.Yellow1
import com.okrama.recipesbook.ui.details.RecipeDetailsScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    state: RecipeDetailsScreenState,
    upPress: () -> Unit,
    onEditRecipe: (Long) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Yellow1)
        ) {
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .imePadding()
                    .fillMaxSize()
                    .background(Yellow1)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageComponent(
                    imageUri = state.imageUrl,
                    modifier = Modifier.aspectRatio(1f),
                    contentDescription = stringResource(id = R.string.recipe_image),
                    cornerRadius = 0.dp,
                    padding = 0.dp,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = state.title,
                    textAlign = TextAlign.Center,
                    style = RecipesBookTheme.typography.headingXLarge,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = state.description,
                    style = RecipesBookTheme.typography.bodyLarge,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                IconButton(
                    onClick = upPress,
                    enabled = true,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 25.dp))
                        .size(35.dp)
                        .background(Yellow0),
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                        tint = Grey0,
                        contentDescription = stringResource(id = R.string.navigate_back)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.padding(8.dp)) {
                IconButton(
                    onClick = { onEditRecipe(state.id) },
                    enabled = true,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 25.dp))
                        .size(35.dp)
                        .background(Yellow0),
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.Edit),
                        tint = Grey0,
                        contentDescription = stringResource(id = R.string.navigate_back)
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(RecipeDetailsScreenStateProvider::class)
    screenState: RecipeDetailsScreenState
) {
    RecipesBookTheme {
        RecipeDetailsScreen(
            state = screenState,
            upPress = {},
            onEditRecipe = {},
        )
    }
}