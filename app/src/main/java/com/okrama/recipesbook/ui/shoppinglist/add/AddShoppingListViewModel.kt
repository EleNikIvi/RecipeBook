package com.okrama.recipesbook.ui.shoppinglist.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.recipe.RecipeInteractor
import com.okrama.recipesbook.domain.shoppinglist.ShoppingListInteractor
import com.okrama.recipesbook.model.EMPTY_LIST_ID
import com.okrama.recipesbook.model.EMPTY_RECIPE_ID
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.navigation.RouteKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EMPTY_LINE = " "

@HiltViewModel
class AddShoppingListViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
    private val shoppingListInteractor: ShoppingListInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _recipeId =
        savedStateHandle.get<Long>(RouteKey.RECIPE_ID_KEY) ?: EMPTY_RECIPE_ID
    private val _listId =
        savedStateHandle.get<Long>(RouteKey.SHOPPING_LIST_ID_KEY) ?: EMPTY_LIST_ID

    private val _persistedState = savedStateHandle.saveableStateFlow(
        key = "recipe-details-view-model-state-key",
        initialValue = constructInitialPersistedState(),
    )
    private val _initialTitle = MutableStateFlow("")
    private val _initialProducts = MutableStateFlow(emptyList<NewProduct>())
    private val _isSaved = MutableStateFlow(false)

    val screenState: StateFlow<AddShoppingListScreenState> = combine(
        _persistedState.asStateFlow(),
        _isSaved,
    ) { persistedState, isSaved ->

        if (!isSaved) {
            AddShoppingListScreenState.Initial(
                title = persistedState.title,
                products = persistedState.products,
                canSave = persistedState.title.isNotBlank() && persistedState.isChanged,
            )
        } else {
            AddShoppingListScreenState.Saved
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddShoppingListScreenState.Initial()
    )

    init {
        viewModelScope.launch {
            if (_recipeId != EMPTY_RECIPE_ID) {
                launch {
                    recipeInteractor.getRecipeWithIngredients(_recipeId)
                        .collect { recipeWithIngredients ->
                            if(recipeWithIngredients.ingredients.isNotEmpty()) {
                                val products =
                                    recipeWithIngredients.ingredients.map { NewProduct(it.ingredient) }
                                _persistedState.update { state ->
                                    state.copy(
                                        products = state.products + products
                                    )
                                }
                                updateIsChanged(true)
                            }
                        }
                }
            }
            if (_listId != EMPTY_LIST_ID) {
                launch {
                    shoppingListInteractor.getShoppingListWithProducts(id = _listId)
                        .collect { shoppingListWithProducts ->
                            _initialTitle.value = shoppingListWithProducts.shoppingList.title
                            _persistedState.update { state ->
                                state.copy(
                                    title = shoppingListWithProducts.shoppingList.title
                                )
                            }
                            if(shoppingListWithProducts.products.isNotEmpty()){
                                val products =
                                    shoppingListWithProducts.products.map { NewProduct(it.product) }
                                _initialProducts.value += products
                                _persistedState.update { state ->
                                    state.copy(
                                        products = state.products + products
                                    )
                                }
                            }
                        }
                }
            }
        }
    }

    fun onListNameChange(name: String) {
        _persistedState.update {
            it.copy(
                title = name,
            )
        }
        updateIsChanged(name != _initialTitle.value)
    }

    fun onProductChange(index: Int, name: String) {
        _persistedState.update {
            val products = it.products.toMutableList()
            products[index] = NewProduct(name)
            it.copy(
                products = products,
            )
        }
        updateIsChanged(name != _initialProducts.value.getOrNull(index)?.name)
    }

    fun onDeleteProduct(index: Int) {
        var deletedItem = _initialProducts.value.getOrNull(index)
        _persistedState.update {
            val products = it.products.toMutableList()
            deletedItem = products.removeAt(index)
            it.copy(
                products = products
            )
        }
        updateIsChanged(deletedItem != _initialProducts.value.getOrNull(index))
    }

    fun onAddNewProduct() {
        _persistedState.update {
            val products = it.products.toMutableList()
            products.add(NewProduct())
            it.copy(
                products = products,
            )
        }
    }

    fun saveShoppingList() {
        viewModelScope.launch {
            val listId = if (_listId != EMPTY_LIST_ID) {
                shoppingListInteractor.updateShoppingList(
                    listId = _listId,
                    title = _persistedState.value.title,
                    products = _persistedState.value.products
                )
            } else {
                shoppingListInteractor.addShoppingList(
                    title = _persistedState.value.title,
                    products = _persistedState.value.products
                )

            }
            if (listId > 0) {
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

    private fun constructInitialPersistedState(): AddShoppingListPersistedState =
        AddShoppingListPersistedState()
}