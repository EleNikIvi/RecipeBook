package com.okrama.recipesbook.ui.shoppinglist.details.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.components.topappbar.SmallTopAppBarWithAction
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.backgroundLight
import com.okrama.recipesbook.ui.core.theme.checkboxColors
import com.okrama.recipesbook.ui.core.theme.onPrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.primaryContainerLight
import com.okrama.recipesbook.ui.shoppinglist.details.ShoppingListDetailsScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListDetailsScreen(
    state: ShoppingListDetailsScreenState,
    upPress: () -> Unit,
    onEditShoppingList: () -> Unit,
    onIsDone: (Long, Boolean) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SmallTopAppBarWithAction(
                title = state.listTitle,
                upPress = upPress,
            ) {
                IconButton(
                    onClick = onEditShoppingList,
                    enabled = true,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(shape = RoundedCornerShape(size = 25.dp))
                        .size(35.dp)
                        .background(primaryContainerLight),
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.Edit),
                        tint = onPrimaryContainerLight,
                        contentDescription = stringResource(id = R.string.button_edit)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(backgroundLight)
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(items = state.products, key = { it.productId }) { product ->
                Row(
                    modifier = Modifier
                        .clickable { onIsDone(product.productId, !product.isDone) }
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = product.isDone,
                        onCheckedChange = { isChecked -> onIsDone(product.productId, isChecked) },
                        colors = checkboxColors,
                    )
                    Text(
                        modifier = Modifier
                            .clickable { onIsDone(product.productId, !product.isDone) },
                        text = product.productName,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        style = RecipesBookTheme.typography.bodyLarge
                            .copy(textDecoration = if (product.isDone) TextDecoration.LineThrough else null),
                    )
                }
            }
        }
    }
}