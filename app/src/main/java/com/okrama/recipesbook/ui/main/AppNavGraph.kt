package com.okrama.recipesbook.ui.main

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
import androidx.navigation.navigation
import com.okrama.recipesbook.ui.addcategory.AddCategoryViewModel
import com.okrama.recipesbook.ui.addcategory.screen.AddCategoryScreen
import com.okrama.recipesbook.ui.addrecipe.AddRecipeViewModel
import com.okrama.recipesbook.ui.addrecipe.screen.AddRecipeScreen
import com.okrama.recipesbook.ui.core.navigation.BottomDest
import com.okrama.recipesbook.ui.core.navigation.RecipesBookNavController
import com.okrama.recipesbook.ui.core.navigation.RecipesDest
import com.okrama.recipesbook.ui.core.navigation.RouteKey.CATEGORY_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.RouteKey.RECIPE_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.RouteKey.SHOPPING_LIST_ID_KEY
import com.okrama.recipesbook.ui.details.RecipeDetailsSideEffect
import com.okrama.recipesbook.ui.details.RecipeDetailsViewModel
import com.okrama.recipesbook.ui.details.screen.RecipeDetailsScreen
import com.okrama.recipesbook.ui.recipes.RecipesViewModel
import com.okrama.recipesbook.ui.recipes.screen.RecipesScreen
import com.okrama.recipesbook.ui.shoppinglist.add.AddShoppingListState
import com.okrama.recipesbook.ui.shoppinglist.add.AddShoppingListViewModel
import com.okrama.recipesbook.ui.shoppinglist.add.Product
import com.okrama.recipesbook.ui.shoppinglist.add.screen.AddToShoppingListScreen

@Composable
fun AppNavGraph(navController: RecipesBookNavController) {
    NavHost(
        navController = navController.navController,
        startDestination = BottomDest.Recipes.route
    ) {
        recipesNavGraph(
            onAddNewRecipeSelected = navController::navigateToAddNewRecipe,
            onRecipeSelected = navController::navigateToRecipeDetails,
            onEditRecipe = navController::navigateToEditRecipe,
            onAddNewCategorySelected = navController::navigateToAddNewCategory,
            onCreateShoppingList = navController::navigateToCreateShoppingList,
            onEditShoppingList = navController::navigateToEditShoppingList,
            upPress = navController::upPress,
            upPressWithResult = navController::upPressWithResult,
        )
        shoppingListNavGraph(
            upPress = navController::upPress,
            upPressWithResult = navController::upPressWithResult,
        )
        settingsNavGraph()
    }
}

private fun NavGraphBuilder.recipesNavGraph(
    onAddNewRecipeSelected: (NavBackStackEntry) -> Unit,
    onRecipeSelected: (Long, NavBackStackEntry) -> Unit,
    onEditRecipe: (Long, NavBackStackEntry) -> Unit,
    onAddNewCategorySelected: (NavBackStackEntry) -> Unit,
    onCreateShoppingList: (Long, NavBackStackEntry) -> Unit,
    onEditShoppingList: (Long, Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    upPressWithResult: (Long, String) -> Unit,
) {
    navigation(
        route = BottomDest.Recipes.route,
        startDestination = RecipesDest.Recipes.route
    ) {
        composable(RecipesDest.Recipes.route) { backStackEntry ->
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
            route = "${RecipesDest.RecipeDetails.route}/{$RECIPE_ID_KEY}",
            arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
        ) { backStackEntry ->
            val viewModel: RecipeDetailsViewModel = hiltViewModel()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = true) {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is RecipeDetailsSideEffect.NavigateToCreateShoppingListScreen -> onCreateShoppingList(
                            sideEffect.recipeId,
                            backStackEntry
                        )

                        is RecipeDetailsSideEffect.NavigateToEditShoppingListScreen -> onEditShoppingList(
                            sideEffect.listId,
                            sideEffect.recipeId,
                            backStackEntry
                        )
                    }
                }
            }

            RecipeDetailsScreen(
                state = screenState,
                upPress = { upPress() },
                onEditRecipe = { recipeId -> onEditRecipe(recipeId, backStackEntry) },
                onShowShoppingList = viewModel::onShowShoppingList,
                onCreateShoppingList = viewModel::onCreateShoppingListScreen,
                onUpdateShoppingList = viewModel::onEditShoppingListScreen,
                onHideShoppingList = viewModel::onHideShoppingList,
            )
        }
        composable(
            "${RecipesDest.EditRecipe.route}/{$RECIPE_ID_KEY}",
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
        composable(RecipesDest.AddRecipe.route) { backStackEntry ->
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
        composable(RecipesDest.AddCategory.route) {
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
        composable(
            "${RecipesDest.CreateShoppingList.route}/{$RECIPE_ID_KEY}",
            arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
        ) {
            val viewModel: AddShoppingListViewModel = hiltViewModel()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            AddToShoppingListScreen(
                state = screenState,
                onListNameChange = viewModel::onListNameChange,
                onProductChange = viewModel::onProductChange,
                onDeleteProduct = viewModel::onDeleteProduct,
                onAddNewProduct = viewModel::onAddNewProduct,
                onSaveList = viewModel::saveShoppingList,
                upPress = upPress,
            )
        }
        composable(
            "${RecipesDest.EditShoppingList.route}/{$SHOPPING_LIST_ID_KEY}/{$RECIPE_ID_KEY}",
            arguments = listOf(navArgument(SHOPPING_LIST_ID_KEY) { type = NavType.LongType },
                navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
        ) {
            val viewModel: AddShoppingListViewModel = hiltViewModel()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            AddToShoppingListScreen(
                state = screenState,
                onListNameChange = viewModel::onListNameChange,
                onProductChange = viewModel::onProductChange,
                onDeleteProduct = viewModel::onDeleteProduct,
                onAddNewProduct = viewModel::onAddNewProduct,
                onSaveList = viewModel::saveShoppingList,
                upPress = upPress,
            )
        }
    }
}

private fun NavGraphBuilder.shoppingListNavGraph(
    upPress: () -> Unit,
    upPressWithResult: (Long, String) -> Unit,
) {
    navigation(
        route = BottomDest.ShoppingList.route,
        startDestination = RecipesDest.ShoppingLists.route
    ) {
        composable(RecipesDest.ShoppingLists.route) {
            AddToShoppingListScreen(
                state = AddShoppingListState.Initial(
                    title = "Title of new recipe",
                    products = listOf(
                        Product("Sugar"),
                        Product("Flour"),
                        Product("Milk"),
                        Product("Eggs")
                    ),
                    canSave = true,
                ),
                onListNameChange = {},
                onProductChange = { _, _ -> },
                onDeleteProduct = {},
                onAddNewProduct = {},
                onSaveList = {},
                upPress = upPress,
            )
        }
    }
}

private fun NavGraphBuilder.settingsNavGraph() {
    navigation(
        route = BottomDest.Settings.route,
        startDestination = RecipesDest.Settings.route
    ) {
        composable(RecipesDest.Settings.route) { backStackEntry ->


        }
    }
}