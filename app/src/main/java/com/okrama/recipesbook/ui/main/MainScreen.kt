package com.okrama.recipesbook.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.okrama.recipesbook.ui.core.DevicePreviews
import com.okrama.recipesbook.ui.core.navigation.isBottomNavRoute
import com.okrama.recipesbook.ui.core.navigation.rememberRecipesBookNavController
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val recipesBookNavController = rememberRecipesBookNavController()
    val currentSelectedScreen by recipesBookNavController.currentScreenAsState()
    val currentRoute by recipesBookNavController.currentRouteAsState()

    Scaffold(
        bottomBar = {
            if (currentRoute == null || isBottomNavRoute(currentRoute)) {
                BottomNavBar(
                    navController = recipesBookNavController,
                    currentSelectedScreen = currentSelectedScreen
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AppNavGraph(appState = recipesBookNavController)
        }
    }
}

@DevicePreviews
@Composable
private fun MainScreenPreview() {
    RecipesBookTheme {
        MainScreen()
    }
}

