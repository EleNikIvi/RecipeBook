package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.components.SearchFieldComponent
import com.okrama.recipesbook.ui.core.theme.Green0
import com.okrama.recipesbook.ui.core.theme.Green3
import com.okrama.recipesbook.ui.core.theme.Yellow1


private val COLLAPSED_TOP_BAR_HEIGHT = 56.dp
private val EXPANDED_TOP_BAR_HEIGHT = 150.dp

@Composable
fun RecipesToolbar(
    searchRequest: String,
    isCollapsed: Boolean,
    onAddNewRecipe: () -> Unit,
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(Yellow1, Green0)))
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = stringResource(id = R.string.app_title),
            textAlign = TextAlign.Start,
            fontSize = 40.sp,
            color = Color.Transparent,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
        )
        AnimatedVisibility(
            visible = !isCollapsed,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                SearchFieldComponent(
                    searchTerm = searchRequest,
                    placeholder = stringResource(id = R.string.search_hint),
                    onSearchTermChange = onSearchTermChange,
                    onSearchFieldClear = onSearchFieldClear,
                    modifier = Modifier
                        .weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                AddNewRecipeButton(EXPANDED_TOOLBAR_ICON_SIZE, onAddNewRecipe)
            }
        }
    }
}

private val COLLAPSED_TOOLBAR_ICON_SIZE = 40.dp
private val EXPANDED_TOOLBAR_ICON_SIZE = 50.dp

@Composable
fun CollapsedToolBar(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean,
    onAddNewRecipe: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(COLLAPSED_TOP_BAR_HEIGHT),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(
            visible = isCollapsed,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
        ) {
            Row(
                modifier = modifier
                    .background(Brush.horizontalGradient(listOf(Yellow1, Green0)))
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AddNewRecipeButton(COLLAPSED_TOOLBAR_ICON_SIZE, onAddNewRecipe)
            }
        }
    }
}

@Composable
fun Title(isCollapsed: Boolean) {
    Text(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .padding(16.dp),
        text = stringResource(id = R.string.app_title),
        textAlign = TextAlign.Start,
        fontSize = if (isCollapsed) 20.sp else 40.sp,
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Bold,
    )
}


@Composable
fun isToolBarCollapsed(
    listState: LazyGridState,
): Boolean {
    val overlapHeightPx = with(LocalDensity.current) {
        EXPANDED_TOP_BAR_HEIGHT.toPx() / 2 - COLLAPSED_TOP_BAR_HEIGHT.toPx()
    }
    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden =
                listState.firstVisibleItemScrollOffset > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }
    return isCollapsed
}

@Composable
private fun AddNewRecipeButton(
    iconSize: Dp,
    onAddNewRecipe: () -> Unit,
) {
    IconButton(onClick = onAddNewRecipe) {
        Icon(
            painter = rememberVectorPainter(image = Icons.TwoTone.Add),
            contentDescription = stringResource(id = R.string.add_new_recipe),
            tint = Green3,
            modifier = Modifier.size(iconSize),
        )
    }
}