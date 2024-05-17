package com.okrama.recipesbook.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
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
    private val categoryInteractor: CategoryInteractor,
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
    private val _category = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-category-key",
        initialValue = "",
    )
    private val _recipeDescription = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-description-key",
        initialValue = "",
    )
    private val _ingredients = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-ingredients-key",
        initialValue = "",
    )

    val screenState: StateFlow<RecipeDetailsScreenState> = combine(
        _recipeImageUri.asStateFlow(),
        _recipeName.asStateFlow(),
        _category.asStateFlow(),
        _recipeDescription.asStateFlow(),
        _ingredients.asStateFlow()
    ) { recipeImageUri, recipeName, category, recipeDescription, ingredients ->

        RecipeDetailsScreenState(
            id = _recipeId,
            imageUrl = recipeImageUri,
            title = recipeName,
            category = category,
            description = recipeDescription,
            ingredients = ingredients
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecipeDetailsScreenState()
    )

    init {
        viewModelScope.launch {
            launch {
                recipeInteractor.getRecipeWithIngredients(_recipeId)
                    .collect { recipeWithIngredients ->
                        _recipeImageUri.value = recipeWithIngredients.recipe.imageUrl
                        _recipeName.value = recipeWithIngredients.recipe.title
                        _recipeDescription.value = recipeWithIngredients.recipe.description
                        _ingredients.value = recipeInteractor.getIngredientsAsString(recipeWithIngredients.ingredients)
                    }
            }
            launch {
                launch {
                    categoryInteractor.getCategoryForRecipe(_recipeId).collect { category ->
                        _category.value = category.title ?: ""
                    }
                }
            }
        }
    }
}