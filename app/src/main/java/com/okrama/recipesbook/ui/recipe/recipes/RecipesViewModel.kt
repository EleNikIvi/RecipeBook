package com.okrama.recipesbook.ui.recipe.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.components.filterrail.model.FILTER_ALL
import com.okrama.recipesbook.ui.core.components.filterrail.model.FilterRailItem
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.model.CategoryUtil
import com.okrama.recipesbook.ui.recipe.recipes.CategoriesFilterRail.getCategoriesFilterRail
import com.okrama.recipesbook.ui.recipe.recipes.CategoriesFilterRail.getCategoryFilterRail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    private val categoryInteractor: CategoryInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _persistedState = savedStateHandle.saveableStateFlow(
        key = "recipes-view-model-state-key",
        initialValue = constructInitialPersistedState(),
    )
    private val _allRecipes = savedStateHandle.saveableStateFlow(
        key = "recipes-view-model-list-key",
        initialValue = emptyList<Recipe>(),
    )
    private val _recipesForCategory = savedStateHandle.saveableStateFlow(
        key = "recipes_view-model-recipes-category-list-key",
        initialValue = emptyList<Recipe>(),
    )
    private val _searchTerm = savedStateHandle.saveableStateFlow(
        key = "recipes-view-model-search-key",
        initialValue = "",
    )

    init {
        viewModelScope.launch {
            launch {
                recipeInteractor.getAllRecipes()
                    .collect { values ->
                        _allRecipes.value = values
                    }
            }
            launch {
                categoryInteractor.getCategories()
                    .collect { values ->
                        _persistedState.update {
                            it.copy(
                                filterCategories = getCategoriesFilterRail(values)
                            )
                        }
                    }
            }
            loadRecipesData()
        }
    }

    private val _sideEffect = MutableSharedFlow<RecipesSideEffect>()
    val sideEffect: SharedFlow<RecipesSideEffect> = _sideEffect.asSharedFlow()

    val screenState: StateFlow<RecipesScreenState> = combine(
        _allRecipes.asStateFlow(),
        _recipesForCategory.asStateFlow(),
        _persistedState.asStateFlow(),
        _searchTerm.asStateFlow(),
    ) { recipes, recipesForCategory, persistedState, searchTerm ->

        val currentRecipes =
            if (persistedState.selectedCategory == FILTER_ALL) recipes
            else recipesForCategory

        val filteredRecipes =
            currentRecipes.filter {
                it.title.contains(
                    other = searchTerm.trim(),
                    ignoreCase = true
                )
            }

        RecipesScreenState(
            recipes = filteredRecipes,
            categories = persistedState.filterCategories.toImmutableList(),
            search = searchTerm,
            selectedCategory = persistedState.selectedCategory,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = constructInitialUiState()
    )

    fun onSearchTermChange(searchTerm: String) {
        _searchTerm.value = searchTerm
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

    fun onRecipeCategoryChange(categoryId: Long) {
        viewModelScope.launch {
            _persistedState.value.filterCategories.find { it.id == categoryId }
                ?.let { selectedCategory ->
                    _persistedState.update {
                        it.copy(selectedCategory = selectedCategory)
                    }
                    loadRecipes(selectedCategory)
                }
        }
    }

    fun onAddRecipeSelected() {
        viewModelScope.launch {
            _sideEffect.emit(
                RecipesSideEffect.NavigateToAddRecipeScreen
            )
        }
    }

    fun onEditRecipeSelected(recipeId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(
                RecipesSideEffect.NavigateToEditRecipeScreen(recipeId)
            )
        }
    }

    fun onRecipeSelected(recipeId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(
                RecipesSideEffect.NavigateToRecipeDetailsScreen(recipeId)
            )
        }
    }

    fun onAddCategorySelected() {
        viewModelScope.launch {
            _sideEffect.emit(
                RecipesSideEffect.NavigateToAddCategoryScreen
            )
        }
    }

    private fun loadRecipesData() {
        viewModelScope.launch {
            val initialVaultCategory = _persistedState.value.selectedCategory
            loadRecipes(category = initialVaultCategory)
        }
    }

    private suspend fun loadRecipes(category: FilterRailItem) {
        viewModelScope.launch {
            val recipesForCategory = recipeInteractor.getRecipesBy(category.id)
            _recipesForCategory.update { recipesForCategory?.recipes ?: emptyList() }
            _persistedState.update {
                it.copy(
                    searchFieldEnabled = !recipesForCategory?.recipes.isNullOrEmpty(),
                )
            }
        }
    }

    private fun constructInitialUiState(): RecipesScreenState = RecipesScreenState()

    private fun constructInitialPersistedState(): RecipesPersistedState = RecipesPersistedState()
}