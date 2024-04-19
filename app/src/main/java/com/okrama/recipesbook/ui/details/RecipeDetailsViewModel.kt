package com.okrama.recipesbook.ui.details

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
class RecipeDetailsViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _recipeId =
        savedStateHandle.get<Long>(MainDestinations.RECIPE_ID_KEY) ?: EMPTY_RECIPE_ID
    private val _recipeImageUri: SaveableStateFlow<String> = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-image-key",
        initialValue = "",
    )
    private val _recipeName = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-name-key",
        initialValue = "",
    )
    private val _recipeDescription = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-description-key",
        initialValue = "",
    )

    val screenState: StateFlow<RecipeDetailsScreenState> = combine(
        _recipeImageUri.asStateFlow(),
        _recipeName.asStateFlow(),
        _recipeDescription.asStateFlow(),
    ) { recipeImageUri, recipeName, recipeDescription ->

        RecipeDetailsScreenState.Initial(
            id = _recipeId,
            imageUrl = recipeImageUri,
            title = recipeName,
            description = recipeDescription,
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecipeDetailsScreenState.Initial()
    )

    init {
        viewModelScope.launch {
            recipeInteractor.getRecipe(_recipeId)
                .collect { recipe ->
                    _recipeImageUri.value = recipe.imageUrl
                    _recipeName.value = recipe.title
                    _recipeDescription.value = recipe.description
                }
        }
    }
}