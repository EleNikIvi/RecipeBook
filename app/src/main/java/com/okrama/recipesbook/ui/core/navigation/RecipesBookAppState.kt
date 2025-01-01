package com.okrama.recipesbook.ui.core.navigation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.okrama.recipesbook.R


sealed class BottomDest(
    val route: String,
    val icon: ImageVector,
    @StringRes val labelStrId: Int
) {
    data object Recipes :
        BottomDest("recipes_bottom_nav", Icons.Default.Home, R.string.recipes_title)

    data object ShoppingList :
        BottomDest(
            "shopping_list_bottom_nav",
            Icons.Default.ShoppingCart,
            R.string.shopping_list_title
        )

    data object Settings :
        BottomDest("settings_bottom_nav", Icons.Default.Settings, R.string.settings_title)

}

/**
 * Destinations used in the [RecipesBookApp].
 */

sealed class RecipesDest(val route: String) {
    data object Recipes : RecipesDest("recipes")
    data object AddRecipe : RecipesDest("addRecipe")
    data object EditRecipe : RecipesDest("editRecipe")
    data object RecipeDetails : RecipesDest("recipeDetail")
    data object AddCategory : RecipesDest("addCategory")
    data object CreateShoppingList : RecipesDest("createShoppingList")
    data object EditShoppingList : RecipesDest("updateShoppingList")
    data object ShoppingLists : RecipesDest("shoppingLists")
    data object ShoppingListDetails : RecipesDest("shoppingListDetails")
    data object Settings : RecipesDest("settings")
}
fun isBottomNavRoute(route: String?) =
    route == RecipesDest.Recipes.route || route == RecipesDest.ShoppingLists.route || route == RecipesDest.Settings.route

object RouteKey {
    const val RECIPE_ID_KEY = "recipeId"
    const val CATEGORY_ID_KEY = "categoryId"
    const val SHOPPING_LIST_ID_KEY = "shoppingListId"
}

/**
 * Remembers and creates an instance of [RecipesBookAppState]
 */
@Composable
fun rememberRecipesBookNavController(
    navController: NavHostController = rememberNavController()
): RecipesBookAppState = remember(navController) {
    RecipesBookAppState(navController)
}

/**
 * Responsible for holding UI Navigation logic.
 */
@Stable
class RecipesBookAppState(
    val navController: NavHostController,
) {

    // ----------------------------------------------------------
    // Navigation state source of truth
    // ----------------------------------------------------------

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun <T> upPressWithResult(data: T?, key: String) {
        // Pass result back
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(key, data)
        navController.navigateUp()
    }

    fun navigateToAddNewRecipe(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(RecipesDest.AddRecipe.route)
        }
    }

    fun navigateToEditRecipe(recipeId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${RecipesDest.EditRecipe.route}/$recipeId")
        }
    }

    fun navigateToRecipeDetails(recipeId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${RecipesDest.RecipeDetails.route}/$recipeId")
        }
    }

    fun navigateToAddNewCategory(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(RecipesDest.AddCategory.route)
        }
    }

    fun navigateToCreateShoppingList(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(RecipesDest.CreateShoppingList.route)
        }
    }

    fun navigateToCreateShoppingListForRecipe(recipeId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${RecipesDest.CreateShoppingList.route}/$recipeId")
        }
    }

    fun navigateToEditShoppingListForRecipe(listId: Long, recipeId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${RecipesDest.EditShoppingList.route}/$listId/$recipeId")
        }
    }

    fun navigateToEditShoppingList(listId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${RecipesDest.EditShoppingList.route}/$listId")
        }
    }

    fun navigateToShoppingListDetails(listId: Long,  from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${RecipesDest.ShoppingListDetails.route}/$listId")
        }
    }

    @Stable
    @Composable
    fun currentScreenAsState(): State<BottomDest> {
        val selectedItem = remember { mutableStateOf<BottomDest>(BottomDest.Recipes) }
        DisposableEffect(key1 = this) {
            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                when {
                    destination.hierarchy.any { it.route == BottomDest.Recipes.route } -> {
                        selectedItem.value = BottomDest.Recipes
                    }

                    destination.hierarchy.any { it.route == BottomDest.ShoppingList.route } -> {
                        selectedItem.value = BottomDest.ShoppingList
                    }

                    destination.hierarchy.any { it.route == BottomDest.Settings.route } -> {
                        selectedItem.value = BottomDest.Settings
                    }
                }

            }
            navController.addOnDestinationChangedListener(listener)
            onDispose {
                navController.removeOnDestinationChangedListener(listener)
            }
        }
        return selectedItem
    }

    @Stable
    @Composable
    fun currentRouteAsState(): State<String?> {
        val selectedItem = remember { mutableStateOf<String?>(null) }
        DisposableEffect(this) {
            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                selectedItem.value = destination.route
            }
            navController.addOnDestinationChangedListener(listener)

            onDispose {
                navController.removeOnDestinationChangedListener(listener)
            }
        }
        return selectedItem
    }

    fun navigateToBottomNavScreen(rootScreen: BottomDest) {

        Log.d("Recipe", "navigateToBottomNavScreen graph ${rootScreen}")
        navController.navigate(rootScreen.route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}