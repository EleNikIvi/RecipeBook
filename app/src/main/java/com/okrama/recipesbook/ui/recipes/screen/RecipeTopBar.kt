package com.okrama.recipesbook.ui.recipes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollState
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
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.ui.core.components.SearchFieldComponent
import com.okrama.recipesbook.ui.core.components.filterrail.FilterRail
import com.okrama.recipesbook.ui.core.theme.Green0
import com.okrama.recipesbook.ui.core.theme.Green3
import com.okrama.recipesbook.ui.core.theme.Yellow1
import com.okrama.recipesbook.ui.recipes.RecipesScreenState


private val COLLAPSED_TOP_BAR_HEIGHT = 56.dp
private val EXPANDED_TOP_BAR_HEIGHT = 150.dp

@Composable
fun RecipesToolbar(
    contentState: RecipesScreenState,
    isCollapsed: Boolean,
    onAddNewRecipe: () -> Unit,
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
    onFilterCategorySelected: (Category) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(Yellow1, Green0)))
            .fillMaxWidth(),
    ) {
        Title(isCollapsed)
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchFieldComponent(
                    searchTerm = contentState.search,
                    placeholder = stringResource(id = R.string.search_hint),
                    onSearchTermChange = onSearchTermChange,
                    onSearchFieldClear = onSearchFieldClear,
                    modifier = Modifier
                        .weight(1f)
                        ,
                )
                Spacer(modifier = Modifier.width(8.dp))
                AddNewRecipeButton(
                    iconSize = 50.dp,
                    onAddNewRecipe = onAddNewRecipe
                )
            }
            FilterRail(
                contentState = contentState,
                onFilterCategorySelected = onFilterCategorySelected
            )
        }
    }
}

@Composable
private fun FilterRail(
    contentState: RecipesScreenState,
    onFilterCategorySelected: (Category) -> Unit,
) {
    val filterRailScrollState = rememberScrollState()

    Column(modifier = Modifier.heightIn(min = 15.dp)) {
        FilterRail(
            filterCategories = contentState.filterCategories,
            selectedCategory = contentState.selectedCategory,
            onFilterCategorySelected = onFilterCategorySelected,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
            scrollState = filterRailScrollState,
        )
    }
}

@Composable
fun Title(isCollapsed: Boolean) {
    val collapsedPaddings = PaddingValues(
        start = 16.dp,
        bottom = 8.dp,
        end = 16.dp,
    )
    val expandedPaddings = PaddingValues(
        vertical = 16.dp,
        horizontal = 16.dp,
    )

    Text(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .padding(if (isCollapsed) collapsedPaddings else expandedPaddings),
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