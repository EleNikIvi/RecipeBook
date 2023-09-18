package com.okrama.recipesbook.ui.addrecipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.core.flow.SaveableStateFlow
import com.okrama.recipesbook.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _recipeImageUri: SaveableStateFlow<String?> = savedStateHandle.saveableStateFlow(
        key = "add_recipe-view-model-image-key",
        initialValue = null,
    )
    private val _recipeName = savedStateHandle.saveableStateFlow(
        key = "add_recipe-view-model-name-key",
        initialValue = "",
    )
    private val _recipeDescription = savedStateHandle.saveableStateFlow(
        key = "add_recipe-view-model-description-key",
        initialValue = "",
    )
    private val _isSaved = savedStateHandle.saveableStateFlow(
        key = "add_recipe-view-model-saved-key",
        initialValue = false,
    )

    val screenState: StateFlow<AddRecipeScreenState> = combine(
        _recipeImageUri.asStateFlow(),
        _recipeName.asStateFlow(),
        _recipeDescription.asStateFlow(),
        _isSaved.asStateFlow(),
    ) { recipeImageUri, recipeName, recipeDescription, isSaved ->
        val canSave = recipeName.isNotBlank() || recipeDescription.isNotBlank()

        if (!isSaved) {
            AddRecipeScreenState.Initial(
                imageUrl = recipeImageUri,
                title = recipeName,
                description = recipeDescription,
                canSave = canSave,
            )
        } else {
            AddRecipeScreenState.Saved
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddRecipeScreenState.Initial()
    )

    fun onImageAdded(imageUrl: String) {
        _recipeImageUri.value = imageUrl
    }

    fun onRecipeNameChange(recipeName: String) {
        _recipeName.value = recipeName
    }

    fun onRecipeDescriptionChange(recipeDescription: String) {
        _recipeDescription.value = recipeDescription
    }

    fun createRecipe() {
        viewModelScope.launch {
            val recipeId = recipeInteractor.addRecipe(
                imageUrl = _recipeImageUri.value ?: "",
                title = _recipeName.value.trim(),
                description = _recipeDescription.value.trim()
            )
            if (recipeId > 0) {
                _isSaved.value = true
            }

        }
    }
}