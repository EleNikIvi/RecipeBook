package com.okrama.recipesbook.ui.addrecipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.model.Category
import com.okrama.recipesbook.model.EMPTY_RECIPE_ID
import com.okrama.recipesbook.ui.addrecipe.Categories.getCategoriesDropdown
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.model.CategoryListProvider
import com.okrama.recipesbook.ui.core.navigation.MainDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    private val categoryInteractor: CategoryInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _recipeId =
        savedStateHandle.get<Long>(MainDestinations.RECIPE_ID_KEY) ?: EMPTY_RECIPE_ID
    private val _persistedState = savedStateHandle.saveableStateFlow(
        key = "add-recipe-view-model-state-key",
        initialValue = constructInitialPersistedState(),
    )

    private val _initialImageUri = MutableStateFlow<String?>(null)
    private val _initialTitle = MutableStateFlow("")
    private val _initialDescription = MutableStateFlow("")
    private val _categories = savedStateHandle.saveableStateFlow(
        key = "categories-view-model-list-key",
        initialValue = emptyList<Category>(),
    )

    private val _isSaved = MutableStateFlow(false)

    val screenState: StateFlow<AddRecipeScreenState> = combine(
        _persistedState.asStateFlow(),
        _categories.asStateFlow(),
        _isSaved,
    ) { persistedState, categories, isSaved ->

        if (!isSaved) {
            val dropdown = getCategoriesDropdown(categories, persistedState.selectedCategory)
            AddRecipeScreenState.Initial(
                imageUrl = persistedState.imageUrl,
                title = persistedState.title,
                description = persistedState.description,
                categoriesDropdown = dropdown,
                canSave = persistedState.isChanged,
            )
        } else {
            AddRecipeScreenState.Saved
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddRecipeScreenState.Initial()
    )

    init {
        viewModelScope.launch {
            if (_recipeId != EMPTY_RECIPE_ID) {
                launch {
                    recipeInteractor.getRecipe(_recipeId)
                        .collect { recipe ->
                            _initialImageUri.value = recipe.imageUrl
                            _initialTitle.value = recipe.title
                            _initialDescription.value = recipe.description
                            _persistedState.update {
                                it.copy(
                                    imageUrl = recipe.imageUrl,
                                    title = recipe.title,
                                    description = recipe.description,
                                )
                            }
                        }
                }
            }
            launch {
                categoryInteractor.getCategories()
                    .collect { values ->
                        _categories.value = CategoryListProvider.getCategories(values)
                    }
            }

        }
    }

    fun onImageAdded(imageUrl: String) {
        _persistedState.update {
            it.copy(
                imageUrl = imageUrl,
            )
        }
        updateIsChanged(imageUrl != _initialImageUri.value)
    }

    fun onRecipeNameChange(recipeName: String) {
        _persistedState.update {
            it.copy(
                title = recipeName,
            )
        }
        updateIsChanged(recipeName != _initialTitle.value)
    }

    fun onRecipeDescriptionChange(recipeDescription: String) {
        _persistedState.update {
            it.copy(
                description = recipeDescription,
            )
        }
        updateIsChanged(recipeDescription != _initialDescription.value)
    }

    fun onCategoryChange(categoryId: Long) {
        _categories.value.find { it.categoryId == categoryId }?.let { selectedCategory ->
            _persistedState.update {
                it.copy(
                    selectedCategory = selectedCategory,
                )
            }
        }
    }

    fun saveRecipe() {
        viewModelScope.launch {
            val recipeId = if (_recipeId != EMPTY_RECIPE_ID) {
                recipeInteractor.updateRecipe(
                    id = _recipeId,
                    imageUrl = _persistedState.value.imageUrl ?: "",
                    title = _persistedState.value.title.trim(),
                    description = _persistedState.value.description.trim(),
                    categoryId = _persistedState.value.selectedCategory.categoryId
                )
            } else {
                recipeInteractor.addRecipe(
                    imageUrl = _persistedState.value.imageUrl ?: "",
                    title = _persistedState.value.title.trim(),
                    description = _persistedState.value.description.trim(),
                    categoryId = _persistedState.value.selectedCategory.categoryId
                )

            }
            if (recipeId > 0) {
                _isSaved.value = true
            }
        }
    }

    private fun updateIsChanged(isChanged: Boolean) {
        _persistedState.update {
            it.copy(
                isChanged = isChanged,
            )
        }
    }

    private fun constructInitialPersistedState(): AddRecipePersistedState =
        AddRecipePersistedState()
}