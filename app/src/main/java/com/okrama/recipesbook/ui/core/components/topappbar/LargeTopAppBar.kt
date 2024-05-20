package com.okrama.recipesbook.ui.core.components.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.okrama.recipesbook.ui.core.components.button.ButtonAdd
import com.okrama.recipesbook.ui.core.components.inputfields.SearchFieldComponent
import com.okrama.recipesbook.ui.core.theme.inversePrimaryLight
import com.okrama.recipesbook.ui.core.theme.onPrimaryContainerLight
import com.okrama.recipesbook.ui.core.theme.primaryLight

private val COLLAPSED_TOP_BAR_HEIGHT = 56.dp
private val EXPANDED_TOP_BAR_HEIGHT = 150.dp

@Composable
fun LargeTopAppBar(
    search: String,
    title: String,
    isCollapsed: Boolean = false,
    searchPlaceholder: String? = null,
    onAction: () -> Unit = {},
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(inversePrimaryLight, primaryLight)))
            .fillMaxWidth(),
    ) {
        Title(
            isCollapsed = isCollapsed,
            title = title,
            onAction = onAction,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            SearchFieldComponent(
                searchTerm = search,
                placeholder = searchPlaceholder,
                onSearchTermChange = onSearchTermChange,
                onSearchFieldClear = onSearchFieldClear,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            )
            content()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun Title(
    isCollapsed: Boolean,
    title: String,
    onAction: () -> Unit,
) {
    val collapsedPaddings = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp,
    )
    val expandedPaddings = PaddingValues(
        vertical = 16.dp,
        horizontal = 16.dp,
    )

    Row(
        modifier = Modifier
            .padding(if (isCollapsed) collapsedPaddings else expandedPaddings),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .zIndex(2f)
                .weight(1f),
            text = title,
            textAlign = TextAlign.Start,
            color = onPrimaryContainerLight,
            fontSize = if (isCollapsed) 20.sp else 40.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(8.dp))
        ButtonAdd(
            onClick = onAction,
        )
    }
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
