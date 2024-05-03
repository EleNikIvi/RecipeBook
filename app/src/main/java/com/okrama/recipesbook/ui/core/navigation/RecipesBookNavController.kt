package com.okrama.recipesbook.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Destinations used in the [RecipesBookApp].
 */
object MainDestinations {
    const val RECIPES_ROUTE = "recipes"
    const val ADD_RECIPE_ROUTE = "addRecipe"
    const val EDIT_RECIPE_ROUTE = "editRecipe"
    const val RECIPES_DETAIL_ROUTE = "recipeDetail"
    const val RECIPE_ID_KEY = "recipeId"
    const val ADD_CATEGORY_ROUTE = "addCategory"
    const val CATEGORY_ID_KEY = "CategoryId"
}

/**
 * Remembers and creates an instance of [RecipesBookNavController]
 */
@Composable
fun rememberRecipesBookNavController(
    navController: NavHostController = rememberNavController()
): RecipesBookNavController = remember(navController) {
    RecipesBookNavController(navController)
}

/**
 * Responsible for holding UI Navigation logic.
 */
@Stable
class RecipesBookNavController(
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

    fun navigateToAddNewRecipe(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(MainDestinations.ADD_RECIPE_ROUTE)
        }
    }

    fun navigateToEditRecipe(recipeId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.EDIT_RECIPE_ROUTE}/$recipeId")
        }
    }

    fun navigateToRecipeDetails(recipeId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.RECIPES_DETAIL_ROUTE}/$recipeId")
        }
    }

    fun navigateToAddNewCategory(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(MainDestinations.ADD_CATEGORY_ROUTE)
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