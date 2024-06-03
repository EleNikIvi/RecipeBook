package com.okrama.recipesbook.ui.recipe.recipes.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

@Composable
fun RecipesEmptyMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.widthIn(max = 544.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_empty_list),
                modifier = Modifier.size(160.dp),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.message_empty),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipesEmptyMessagePreview() {
    RecipesBookTheme {
        RecipesEmptyMessage()
    }
}