package com.okrama.recipesbook.ui.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _recipes = savedStateHandle.saveableStateFlow(
        key = "recipes-view-model-list-key",
        initialValue = emptyList<Recipe>(),
    )
    private val _searchTerm = savedStateHandle.saveableStateFlow(
        key = "recipes-view-model-search-key",
        initialValue = "",
    )

    init {
        viewModelScope.launch {
            recipeInteractor.getRecipes()
                .collect { values ->
                    _recipes.value = values
                }
        }
    }

    val screenState: StateFlow<RecipesScreenState> = combine(
        _recipes.asStateFlow(),
        _searchTerm.asStateFlow(),
    ) { recipes, searchTerm ->

        val filteredRecipes =
            recipes.filter { it.title.contains(other = searchTerm, ignoreCase = true) }

        RecipesScreenState(
            recipes = filteredRecipes,
            search = searchTerm,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = constructInitialUiState()
    )

    fun onSearchTermChange(searchTerm: String) {
        _searchTerm.value = searchTerm.trim()
    }

    fun onSearchFieldClear() {
        _searchTerm.value = ""
    }

    fun onDeleteRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeInteractor.deleteRecipe(
                recipeId = recipeId,
            )
        }
    }

    fun onRecipeCategoryChange(vaultFilterCategory: Category) {
        viewModelScope.launch {
            // TODO filter by category
        }
    }

    private fun constructInitialUiState(): RecipesScreenState = RecipesScreenState()
}