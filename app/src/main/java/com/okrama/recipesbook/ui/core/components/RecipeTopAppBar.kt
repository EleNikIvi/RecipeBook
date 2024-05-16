package com.okrama.recipesbook.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.inversePrimaryLight
import com.okrama.recipesbook.ui.core.theme.onPrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopAppBar(
    title: String,
    upPress: () -> Unit,
    onSave: () -> Unit,
    canSave: Boolean,
) {
    val focusManager = LocalFocusManager.current
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = title,
                textAlign = TextAlign.Center,
                style = RecipesBookTheme.typography.headingXLarge,
            )
        },
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(inversePrimaryLight, primaryLight))),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            IconButton(
                onClick = upPress,
                enabled = true,
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                    tint = onPrimaryContainerLight,
                    contentDescription = stringResource(id = R.string.navigate_back),
                )
            }
        },
        actions = {
            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    onSave()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = onPrimaryContainerLight,
                ),
                enabled = canSave,
            ) {
                Text(
                    text = stringResource(id = R.string.button_save),
                    style = RecipesBookTheme.typography.headingSmall,
                )
            }
        },
    )
}

@Preview
@Composable
private fun RecipeTopAppBarEnabledPreview() {
    RecipesBookTheme {
        RecipeTopAppBar(
            title = "New Category",
            upPress = {},
            onSave = {},
            canSave = true,
        )
    }
}

@Preview
@Composable
private fun RecipeTopAppBarDisabledPreview() {
    RecipesBookTheme {
        RecipeTopAppBar(
            title = "New Category",
            upPress = {},
            onSave = {},
            canSave = false,
        )
    }
}