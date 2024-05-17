package com.okrama.recipesbook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.okrama.recipesbook.ui.addcategory.AddCategoryViewModel
import com.okrama.recipesbook.ui.addcategory.screen.AddCategoryScreen
import com.okrama.recipesbook.ui.addrecipe.AddRecipeViewModel
import com.okrama.recipesbook.ui.addrecipe.screen.AddRecipeScreen
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.ADD_CATEGORY_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.ADD_RECIPE_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.CATEGORY_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.EDIT_RECIPE_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.RECIPES_DETAIL_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.RECIPES_ROUTE
import com.okrama.recipesbook.ui.core.navigation.MainDestinations.RECIPE_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.rememberRecipesBookNavController
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
            onEditRecipe = recipesBookNavController::navigateToEditRecipe,
            onAddNewCategorySelected = recipesBookNavController::navigateToAddNewCategory,
            upPress = recipesBookNavController::upPress,
            upPressWithResult = recipesBookNavController::upPressWithResult
        )
    }
}

private fun NavGraphBuilder.recipesBookNavGraph(
    onAddNewRecipeSelected: (NavBackStackEntry) -> Unit,
    onRecipeSelected: (Long, NavBackStackEntry) -> Unit,
    onEditRecipe: (Long, NavBackStackEntry) -> Unit,
    onAddNewCategorySelected: (NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    upPressWithResult: (Long, String) -> Unit,
) {
    composable(RECIPES_ROUTE) { backStackEntry ->
        val viewModel: RecipesViewModel = hiltViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()

        RecipesScreen(
            screenState = screenState,
            onAddNewRecipe = { onAddNewRecipeSelected(backStackEntry) },
            onEditRecipe = { recipeId -> onEditRecipe(recipeId, backStackEntry) },
            onRecipeSelected = { recipeId -> onRecipeSelected(recipeId, backStackEntry) },
            onSearchTermChange = viewModel::onSearchTermChange,
            onSearchFieldClear = viewModel::onSearchFieldClear,
            onDeleteRecipe = viewModel::onDeleteRecipe,
            onRecipeCategoryChange = viewModel::onRecipeCategoryChange,
            onAddNewCategory = { onAddNewCategorySelected(backStackEntry) }
        )
    }
    composable(
        "${RECIPES_DETAIL_ROUTE}/{${RECIPE_ID_KEY}}",
        arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val viewModel: RecipeDetailsViewModel = hiltViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        RecipeDetailsScreen(
            state = screenState,
            upPress = { upPress() },
            onEditRecipe = { recipeId -> onEditRecipe(recipeId, backStackEntry) }
        )
    }
    composable(
        "${EDIT_RECIPE_ROUTE}/{${RECIPE_ID_KEY}}",
        arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val viewModel: AddRecipeViewModel = hiltViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        AddRecipeScreen(
            state = screenState,
            onImageAdded = viewModel::onImageAdded,
            onRecipeNameChange = viewModel::onRecipeNameChange,
            onRecipeDescriptionChange = viewModel::onRecipeDescriptionChange,
            onCategoryChange = viewModel::onCategoryChange,
            onIngredientsChange = viewModel::onIngredientsChange,
            onSaveRecipe = viewModel::saveRecipe,
            onAddNewCategory = { onAddNewCategorySelected(backStackEntry) },
            upPress = upPress,
        )
    }
    composable(ADD_RECIPE_ROUTE) { backStackEntry ->
        val viewModel: AddRecipeViewModel = hiltViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = true) {
            backStackEntry.savedStateHandle.get<Long>(CATEGORY_ID_KEY)?.let { newCategoryId ->
                viewModel.onCategoryChange(newCategoryId)
            }
        }
        AddRecipeScreen(
            state = screenState,
            onImageAdded = viewModel::onImageAdded,
            onRecipeNameChange = viewModel::onRecipeNameChange,
            onRecipeDescriptionChange = viewModel::onRecipeDescriptionChange,
            onCategoryChange = viewModel::onCategoryChange,
            onIngredientsChange = viewModel::onIngredientsChange,
            onSaveRecipe = viewModel::saveRecipe,
            onAddNewCategory = { onAddNewCategorySelected(backStackEntry) },
            upPress = upPress,
        )
    }
    composable(ADD_CATEGORY_ROUTE) {
        val viewModel: AddCategoryViewModel = hiltViewModel()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()

        AddCategoryScreen(
            state = screenState,
            onCategoryNameChange = viewModel::onCategoryNameChange,
            onSaveCategory = viewModel::saveCategory,
            upPress = upPress,
            upPressWithResult = { id -> upPressWithResult(id, CATEGORY_ID_KEY) }
        )
    }
}