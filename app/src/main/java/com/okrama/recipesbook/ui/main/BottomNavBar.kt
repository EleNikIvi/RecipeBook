package com.okrama.recipesbook.ui.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.okrama.recipesbook.ui.core.navigation.BottomDest
import com.okrama.recipesbook.ui.core.navigation.RecipesBookAppState
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.onPrimaryLight
import com.okrama.recipesbook.ui.core.theme.primaryLight
import com.okrama.recipesbook.ui.core.theme.tertiaryContainerLight

@Composable
fun BottomNavBar(
    navController: RecipesBookAppState,
    currentSelectedScreen: BottomDest,
) {
    NavigationBar(
        containerColor = primaryLight,
    ) {
        RecipeNavigationBarItem(
            navController = navController,
            selectedScreen = currentSelectedScreen,
            bottomDest = BottomDest.Recipes,
        )
        RecipeNavigationBarItem(
            navController = navController,
            selectedScreen = currentSelectedScreen,
            bottomDest = BottomDest.ShoppingList,
        )
        RecipeNavigationBarItem(
            navController = navController,
            selectedScreen = currentSelectedScreen,
            bottomDest = BottomDest.Settings,
        )
    }
}

@Composable
private fun RowScope.RecipeNavigationBarItem(
    navController: RecipesBookAppState,
    selectedScreen: BottomDest,
    bottomDest: BottomDest,
) {
    NavigationBarItem(
        selected = selectedScreen == bottomDest,
        onClick = { navController.navigateToBottomNavScreen(bottomDest) },
        alwaysShowLabel = true,
        label = {
            Text(
                color = onPrimaryLight,
                style = RecipesBookTheme.typography.bodyMedium,
                text = stringResource(id = bottomDest.labelStrId)
            )
        },
        icon = {
            Icon(
                painter = rememberVectorPainter(image = bottomDest.icon),
                contentDescription = stringResource(id = bottomDest.labelStrId),
            )
        },
        colors = NavigationBarItemDefaults
            .colors(
                selectedIconColor = tertiaryContainerLight,
                indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    LocalAbsoluteTonalElevation.current
                )
            )
    )
}