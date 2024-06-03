package com.okrama.recipesbook.ui.recipe.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.domain.shoppinglist.ShoppingListInteractor
import com.okrama.recipesbook.model.EMPTY_RECIPE_ID
import com.okrama.recipesbook.model.ShoppingList
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.navigation.RouteKey.RECIPE_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    private val categoryInteractor: CategoryInteractor,
    private val shoppingListInteractor: ShoppingListInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<RecipeDetailsSideEffect>()
    val sideEffect: SharedFlow<RecipeDetailsSideEffect> = _sideEffect.asSharedFlow()

    private val _recipeId =
        savedStateHandle.get<Long>(RECIPE_ID_KEY) ?: EMPTY_RECIPE_ID

    private val _persistedState = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-state-key",
        initialValue = constructInitialPersistedState(),
    )
    private val _shoppingLists = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-shopping-list-key",
        initialValue = emptyList<ShoppingList>(),
    )
    private val _selectedShoppingList = MutableStateFlow<ShoppingList?>(null)
    private val _dialog = MutableStateFlow<Dialog>(Dialog.None)

    val screenState: StateFlow<RecipeDetailsScreenState> = combine(
        _persistedState.asStateFlow(),
        _shoppingLists.asStateFlow(),
        _selectedShoppingList.asStateFlow(),
        _dialog.asStateFlow()
    ) { persistedState, shoppingLists, selectedShoppingList, dialog ->

        val dropdown = ShoppingLists.getShoppingListsDropdown(shoppingLists, selectedShoppingList)
        RecipeDetailsScreenState(
            id = _recipeId,
            imageUrl = persistedState.imageUrl,
            title = persistedState.title,
            category = persistedState.category,
            description = persistedState.description,
            ingredients = persistedState.ingredients,
            shoppingListDropdown = dropdown,
            dialog = dialog,
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
                        _persistedState.update {
                            it.copy(
                                imageUrl = recipeWithIngredients.recipe.imageUrl,
                                title = recipeWithIngredients.recipe.title,
                                description = recipeWithIngredients.recipe.description,
                                ingredients = recipeInteractor.getIngredientsAsString(
                                    recipeWithIngredients.ingredients
                                )
                            )
                        }
                    }
            }
            launch {
                categoryInteractor.getCategoryForRecipe(_recipeId).collect { category ->
                    _persistedState.update {
                        it.copy(
                            category = category.title ?: ""
                        )
                    }
                }
            }
            launch {
                shoppingListInteractor.getAllShoppingLists()
                    .collect { shoppingLists ->
                        _shoppingLists.value = shoppingLists
                    }
            }
        }
    }

    fun onShowShoppingList() {
        _dialog.value = Dialog.ShowShoppingListDialog
    }

    fun onHideShoppingList() {
        _dialog.value = Dialog.None
    }

    fun onCreateShoppingListScreen() {
        onHideShoppingList()
        viewModelScope.launch {
            _sideEffect.emit(
                RecipeDetailsSideEffect.NavigateToCreateShoppingListScreen(
                    _recipeId
                )
            )
        }
    }

    fun onEditShoppingListScreen(listId: Long) {
        onHideShoppingList()
        viewModelScope.launch {
            _sideEffect.emit(
                RecipeDetailsSideEffect.NavigateToEditShoppingListScreen(
                    listId,
                    _recipeId
                )
            )
        }
    }

    private fun constructInitialPersistedState(): RecipeDetailsPersistedState =
        RecipeDetailsPersistedState()
}