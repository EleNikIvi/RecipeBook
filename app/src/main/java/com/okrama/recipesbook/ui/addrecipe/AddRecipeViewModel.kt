package com.okrama.recipesbook.ui.addrecipe

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.model.EMPTY_RECIPE_ID
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.navigation.MainDestinations
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

    private val _recipeId =
        savedStateHandle.get<Long>(MainDestinations.RECIPE_ID_KEY) ?: EMPTY_RECIPE_ID

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

    init {
        if (_recipeId != EMPTY_RECIPE_ID) {
            viewModelScope.launch {
                recipeInteractor.getRecipe(_recipeId)
                    .collect { recipe ->
                        _recipeImageUri.value = recipe.imageUrl
                        _recipeName.value = recipe.title
                        _recipeDescription.value = recipe.description
                        Log.d("AddRecipeViewModel", "init $recipe")
                    }
            }
        }
    }

    fun onImageAdded(imageUrl: String) {
        _recipeImageUri.value = imageUrl
    }

    fun onRecipeNameChange(recipeName: String) {
        _recipeName.value = recipeName
    }

    fun onRecipeDescriptionChange(recipeDescription: String) {
        _recipeDescription.value = recipeDescription
    }

    fun saveRecipe() {
        viewModelScope.launch {
            val recipeId = if (_recipeId != EMPTY_RECIPE_ID) {
                recipeInteractor.updateRecipe(
                    id = _recipeId,
                    imageUrl = _recipeImageUri.value ?: "",
                    title = _recipeName.value.trim(),
                    description = _recipeDescription.value.trim()
                )
            } else {
                recipeInteractor.addRecipe(
                    imageUrl = _recipeImageUri.value ?: "",
                    title = _recipeName.value.trim(),
                    description = _recipeDescription.value.trim()
                )

            }
            if (recipeId > 0) {
                _isSaved.value = true
            }
        }
    }
}