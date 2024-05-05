package com.okrama.recipesbook.ui.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.Recipe
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.model.CategoryListProvider
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
        key = "recipes_category-view-model-list-key",
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
                                filterCategories = CategoryListProvider.getCategories(values)
                            )
                        }
                    }
            }
            loadRecipesData()
        }
    }

    val screenState: StateFlow<RecipesScreenState> = combine(
        _allRecipes.asStateFlow(),
        _recipesForCategory.asStateFlow(),
        _persistedState.asStateFlow(),
        _searchTerm.asStateFlow(),
    ) { recipes, recipesForCategory, persistedState, searchTerm ->

        val currentRecipes =
            if (persistedState.selectedCategory == CategoryListProvider.CATEGORY_ALL) recipes
            else recipesForCategory

        val filteredRecipes =
            currentRecipes.filter { it.title.contains(other = searchTerm, ignoreCase = true) }

        RecipesScreenState(
            recipes = filteredRecipes,
            categories = persistedState.filterCategories,
            search = searchTerm,
            selectedCategory = persistedState.selectedCategory,
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

    fun onRecipeCategoryChange(category: Category) {
        viewModelScope.launch {
            loadRecipes(category)
        }
    }

    private fun loadRecipesData() {
        viewModelScope.launch {
            val initialVaultCategory = _persistedState.value.selectedCategory
            loadRecipes(category = initialVaultCategory)
        }
    }

    private suspend fun loadRecipes(category: Category) {
        viewModelScope.launch {
            val recipes = recipeInteractor.getRecipesBy(category.categoryId)
            _recipesForCategory.update { recipes?.recipes ?: emptyList() }
            _persistedState.update {
                it.copy(
                    searchFieldEnabled = !recipes?.recipes.isNullOrEmpty(),
                    selectedCategory = category,
                )
            }
        }
    }

    private fun constructInitialUiState(): RecipesScreenState = RecipesScreenState()

    private fun constructInitialPersistedState(): RecipesPersistedState = RecipesPersistedState()
}