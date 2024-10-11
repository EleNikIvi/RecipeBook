package com.okrama.recipesbook.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.okrama.recipesbook.ui.category.addcategory.AddCategorySideEffect
import com.okrama.recipesbook.ui.category.addcategory.AddCategoryViewModel
import com.okrama.recipesbook.ui.category.addcategory.screen.AddCategoryScreen
import com.okrama.recipesbook.ui.core.navigation.BottomDest
import com.okrama.recipesbook.ui.core.navigation.RecipesBookNavController
import com.okrama.recipesbook.ui.core.navigation.RecipesDest
import com.okrama.recipesbook.ui.core.navigation.RouteKey.CATEGORY_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.RouteKey.RECIPE_ID_KEY
import com.okrama.recipesbook.ui.core.navigation.RouteKey.SHOPPING_LIST_ID_KEY
import com.okrama.recipesbook.ui.recipe.addrecipe.AddRecipeViewModel
import com.okrama.recipesbook.ui.recipe.addrecipe.screen.AddRecipeScreen
import com.okrama.recipesbook.ui.recipe.details.RecipeDetailsSideEffect
import com.okrama.recipesbook.ui.recipe.details.RecipeDetailsViewModel
import com.okrama.recipesbook.ui.recipe.details.screen.RecipeDetailsScreen
import com.okrama.recipesbook.ui.recipe.recipes.RecipesSideEffect
import com.okrama.recipesbook.ui.recipe.recipes.RecipesViewModel
import com.okrama.recipesbook.ui.recipe.recipes.screen.RecipesScreen
import com.okrama.recipesbook.ui.shoppinglist.add.AddShoppingListViewModel
import com.okrama.recipesbook.ui.shoppinglist.add.screen.AddShoppingListScreen
import com.okrama.recipesbook.ui.shoppinglist.details.ShoppingListDetailsSideEffect
import com.okrama.recipesbook.ui.shoppinglist.details.ShoppingListDetailsViewModel
import com.okrama.recipesbook.ui.shoppinglist.details.screen.ShoppingListDetailsScreen
import com.okrama.recipesbook.ui.shoppinglist.list.ShoppingListsSideEffect
import com.okrama.recipesbook.ui.shoppinglist.list.ShoppingListsViewModel
import com.okrama.recipesbook.ui.shoppinglist.list.screen.ShoppingListsScreen

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
            onAddCategorySelected = navController::navigateToAddNewCategory,
            onCreateShoppingList = navController::navigateToCreateShoppingListForRecipe,
            onEditShoppingList = navController::navigateToEditShoppingListForRecipe,
            upPress = navController::upPress,
            upPressWithResult = navController::upPressWithResult,
        )
        shoppingListNavGraph(
            onCreateShoppingList = navController::navigateToCreateShoppingList,
            onShoppingListDetails = navController::navigateToShoppingListDetails,
            onEditShoppingList = navController::navigateToEditShoppingList,
            upPress = navController::upPress,
        )
        settingsNavGraph()
    }
}

private fun NavGraphBuilder.recipesNavGraph(
    onAddNewRecipeSelected: (NavBackStackEntry) -> Unit,
    onRecipeSelected: (Long, NavBackStackEntry) -> Unit,
    onEditRecipe: (Long, NavBackStackEntry) -> Unit,
    onAddCategorySelected: (NavBackStackEntry) -> Unit,
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

            val lifecycleOwner = LocalLifecycleOwner.current
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is RecipesSideEffect.NavigateToAddRecipeScreen -> onAddNewRecipeSelected(
                                backStackEntry
                            )

                            is RecipesSideEffect.NavigateToEditRecipeScreen -> onEditRecipe(
                                sideEffect.recipeId,
                                backStackEntry
                            )

                            is RecipesSideEffect.NavigateToRecipeDetailsScreen -> onRecipeSelected(
                                sideEffect.recipeId,
                                backStackEntry
                            )

                            is RecipesSideEffect.NavigateToAddCategoryScreen -> onAddCategorySelected(
                                backStackEntry
                            )
                        }
                    }
                }
            }

            RecipesScreen(
                screenState = screenState,
                onAddNewRecipe = viewModel::onAddRecipeSelected,
                onEditRecipe = { recipeId -> onEditRecipe(recipeId, backStackEntry) },
                onRecipeSelected = { recipeId -> onRecipeSelected(recipeId, backStackEntry) },
                onSearchTermChange = viewModel::onSearchTermChange,
                onSearchFieldClear = viewModel::onSearchFieldClear,
                onDeleteRecipe = viewModel::onDeleteRecipe,
                onRecipeCategoryChange = viewModel::onRecipeCategoryChange,
                onAddNewCategory = { onAddCategorySelected(backStackEntry) }
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
                onAddNewCategory = { onAddCategorySelected(backStackEntry) },
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
                onAddNewCategory = { onAddCategorySelected(backStackEntry) },
                upPress = upPress,
            )
        }
        composable(RecipesDest.AddCategory.route) {
            val viewModel: AddCategoryViewModel = hiltViewModel()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = true) {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is AddCategorySideEffect.OnCategorySaved -> upPressWithResult(
                            sideEffect.categoryId,
                            CATEGORY_ID_KEY
                        )
                    }
                }
            }

            AddCategoryScreen(
                state = screenState,
                onCategoryNameChange = viewModel::onCategoryNameChange,
                onSaveCategory = viewModel::saveCategory,
                upPress = upPress,
            )
        }
        composable(RecipesDest.CreateShoppingList.route) {
            AddShoppingListScreen(upPress = upPress)
        }
        composable(
            "${RecipesDest.CreateShoppingList.route}/{$RECIPE_ID_KEY}",
            arguments = listOf(navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
        ) {
            AddShoppingListScreen(upPress = upPress)
        }
        composable(
            "${RecipesDest.EditShoppingList.route}/{$SHOPPING_LIST_ID_KEY}/{$RECIPE_ID_KEY}",
            arguments = listOf(navArgument(SHOPPING_LIST_ID_KEY) { type = NavType.LongType },
                navArgument(RECIPE_ID_KEY) { type = NavType.LongType })
        ) {
            AddShoppingListScreen(upPress = upPress)
        }
    }
}

@Composable
private fun AddShoppingListScreen(
    upPress: () -> Unit,
) {
    val viewModel: AddShoppingListViewModel = hiltViewModel()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AddShoppingListScreen(
        state = screenState,
        onListNameChange = viewModel::onListNameChange,
        onProductChange = viewModel::onProductChange,
        onDeleteProduct = viewModel::onDeleteProduct,
        onAddNewProduct = viewModel::onAddNewProduct,
        onSaveList = viewModel::saveShoppingList,
        upPress = upPress,
    )
}

private fun NavGraphBuilder.shoppingListNavGraph(
    onCreateShoppingList: (NavBackStackEntry) -> Unit,
    onShoppingListDetails: (Long, NavBackStackEntry) -> Unit,
    onEditShoppingList: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
) {
    navigation(
        route = BottomDest.ShoppingList.route,
        startDestination = RecipesDest.ShoppingLists.route
    ) {
        composable(RecipesDest.ShoppingLists.route) { backStackEntry ->
            val viewModel: ShoppingListsViewModel = hiltViewModel()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = true) {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is ShoppingListsSideEffect.NavigateToCreateShoppingListScreen ->
                            onCreateShoppingList(backStackEntry)

                        is ShoppingListsSideEffect.NavigateToShoppingListDetailsScreen -> onShoppingListDetails(
                            sideEffect.listId,
                            backStackEntry
                        )
                    }
                }
            }

            ShoppingListsScreen(
                screenState = screenState,
                onAddShoppingList = viewModel::onAddShoppingList,
                onShoppingListDetails = viewModel::onShoppingListDetails,
                onSearchTermChange = viewModel::onSearchTermChange,
                onSearchFieldClear = viewModel::onSearchFieldClear,
            )
        }

        composable(
            "${RecipesDest.ShoppingListDetails.route}/{$SHOPPING_LIST_ID_KEY}",
            arguments = listOf(navArgument(SHOPPING_LIST_ID_KEY) { type = NavType.LongType })
        ) { backStackEntry ->
            val viewModel: ShoppingListDetailsViewModel = hiltViewModel()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = true) {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is ShoppingListDetailsSideEffect.NavigateToEditShoppingListScreen ->
                            onEditShoppingList(sideEffect.listId, backStackEntry)

                        is ShoppingListDetailsSideEffect.NavigateUp -> upPress()
                    }
                }
            }

            ShoppingListDetailsScreen(
                state = screenState,
                upPress = viewModel::onNavigateUp,
                onEditShoppingList = viewModel::onEditShoppingList,
                onIsDone = viewModel::updateProductIsDone,
            )
        }

        composable(
            "${RecipesDest.EditShoppingList.route}/{$SHOPPING_LIST_ID_KEY}",
            arguments = listOf(navArgument(SHOPPING_LIST_ID_KEY) { type = NavType.LongType })
        ) {
            AddShoppingListScreen(upPress = upPress)
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