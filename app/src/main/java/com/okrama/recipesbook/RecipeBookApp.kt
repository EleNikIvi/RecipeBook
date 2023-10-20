package com.okrama.recipesbook

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.okrama.recipesbook.ui.addrecipe.AddRecipeViewModel
import com.okrama.recipesbook.ui.addrecipe.screen.AddRecipeScreen
import com.okrama.recipesbook.ui.core.ui.navigation.MainDestinations.ADD_RECIPE_ROUTE
import com.okrama.recipesbook.ui.core.ui.navigation.MainDestinations.RECIPES_DETAIL_ROUTE
import com.okrama.recipesbook.ui.core.ui.navigation.MainDestinations.RECIPES_ROUTE
import com.okrama.recipesbook.ui.core.ui.navigation.MainDestinations.RECIPE_ID_KEY
import com.okrama.recipesbook.ui.core.ui.navigation.rememberRecipesBookNavController
import com.okrama.recipesbook.ui.details.RecipeDetailsViewModel
import com.okrama.recipesbook.ui.details.screen.RecipeDetailsScreen
import com.okrama.recipesbook.ui.recipes.RecipesViewModel
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
            upPress = recipesBookNavController::upPress,
        )
    }
}

private fun NavGraphBuilder.recipesBookNavGraph(
    onAddNewRecipeSelected: (NavBackStackEntry) -> Unit,
    onRecipeSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
) {
    composable(RECIPES_ROUTE) { backStackEntry ->
        val recipesViewModel = hiltViewModel<RecipesViewModel>()
        RecipesScreen(
            onAddNewRecipe = { onAddNewRecipeSelected(backStackEntry) },
            onRecipeSelected = { recipeId -> onRecipeSelected(recipeId, backStackEntry) },
            viewModel = recipesViewModel,
        )
    }
    composable(
        "${RECIPES_DETAIL_ROUTE}/{${RECIPE_ID_KEY}}",
        arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val recipeId = arguments.getLong(RECIPE_ID_KEY)
        val recipeDetailsViewModel = hiltViewModel<RecipeDetailsViewModel>()
        RecipeDetailsScreen(
            recipeId = recipeId,
            upPress = { upPress() },
            viewModel = recipeDetailsViewModel,
        )
    }
    composable(ADD_RECIPE_ROUTE) {
        val addRecipeViewModel = hiltViewModel<AddRecipeViewModel>()
        AddRecipeScreen(
            upPress = { upPress() },
            viewModel = addRecipeViewModel,
        )
    }
}