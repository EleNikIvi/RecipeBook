package com.okrama.recipesbook

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.okrama.recipesbook.ui.addrecipe.screen.AddRecipeScreen
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.ADD_RECIPE_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.EDIT_RECIPE_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.RECIPES_DETAIL_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.RECIPES_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.RECIPE_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.rememberRecipesBookNavController
import com.okrama.recipesbook.ui.details.screen.RecipeDetailsScreen
import com.okrama.recipesbook.ui.recipes.screen.RecipesScreen

@Composable
fun RecipesBookApp() {
    val recipesBookNavController = rememberRecipesBookNavController()
    NavHost(
        navController = recipesBookNavController.navController,
        startDestination = RECIPES_ROUTE
    ) {
        recipesBookNavGraph(
            onAddNewRecipeSelected = recipesBookNavController::navigateToAddNewRecipe,
            onRecipeSelected = recipesBookNavController::navigateToRecipeDetails,
            onEditRecipe = recipesBookNavController::navigateToEditRecipe,
            upPress = recipesBookNavController::upPress,
        )
    }
}

private fun NavGraphBuilder.recipesBookNavGraph(
    onAddNewRecipeSelected: (NavBackStackEntry) -> Unit,
    onRecipeSelected: (Long, NavBackStackEntry) -> Unit,
    onEditRecipe: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
) {
    composable(RECIPES_ROUTE) { backStackEntry ->
        LaunchedEffect(key1 = true) {
            Log.d("RecipesBookApp", "RECIPES_ROUTE")
        }
        RecipesScreen(
            onAddNewRecipe = { onAddNewRecipeSelected(backStackEntry) },
            onRecipeSelected = { recipeId -> onRecipeSelected(recipeId, backStackEntry) },
        )
    }
    composable(
        "${RECIPES_DETAIL_ROUTE}/{${RECIPE_ID_KEY}}",
        arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        LaunchedEffect(key1 = true) {
            Log.d("RecipesBookApp", "RECIPES_DETAIL_ROUTE")
        }
        RecipeDetailsScreen(
            upPress = { upPress() },
            onEditRecipe = { recipeId -> onEditRecipe(recipeId, backStackEntry) }
        )
    }
    composable(
        "${EDIT_RECIPE_ROUTE}/{${RECIPE_ID_KEY}}",
        arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
    ) {
        LaunchedEffect(key1 = true) {
            Log.d("RecipesBookApp", "EDIT_RECIPE_ROUTE}/{RECIPE_ID_KEY")
        }
        AddRecipeScreen(
            upPress = { upPress() },
        )
    }
    composable(ADD_RECIPE_ROUTE) {
        LaunchedEffect(key1 = true) {
            Log.d("RecipesBookApp", "ADD_RECIPE_ROUTE")
        }
        AddRecipeScreen(
            upPress = { upPress() },
        )
    }
}