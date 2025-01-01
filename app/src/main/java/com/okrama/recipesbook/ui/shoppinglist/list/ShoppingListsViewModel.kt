package com.okrama.recipesbook.ui.shoppinglist.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.shoppinglist.ShoppingListInteractor
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListsViewModel @Inject constructor(
    private val shoppingListInteractor: ShoppingListInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _allShoppingLists = savedStateHandle.saveableStateFlow(
        key = "shopping-list-view-model-list-key",
        initialValue = emptyList<ShoppingListModel>(),
    )

    private val _searchTerm = savedStateHandle.saveableStateFlow(
        key = "shopping-list-view-model-search-key",
        initialValue = "",
    )

    init {
        viewModelScope.launch {
            shoppingListInteractor.getAllShoppingListsWithProducts()
                .collect { values ->
                    _allShoppingLists.value = values.map {
                        ShoppingListModel(
                            listId = it.shoppingList.listId,
                            listTitle = it.shoppingList.title,
                            products = shoppingListInteractor.getProductsAsString(it.products),
                        )
                    }
                }
        }
    }

    private val _sideEffect = MutableSharedFlow<ShoppingListsSideEffect>()
    val sideEffect: SharedFlow<ShoppingListsSideEffect> = _sideEffect.asSharedFlow()


    // StateFlow with a Flow operator
    private val _color = MutableStateFlow(0xFFFFFF)
    //val color = _color.asStateFlow()
    val nextColor = _color.map {
        it + 1
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0xFFFFFF
    )

    // State with a State "operator"
    var color by mutableStateOf(0xFFFFFF)
        private set
    val complementaryColor
        get() = color + 1

    val screenState: StateFlow<ShoppingListScreenState> = combine(
        _allShoppingLists.asStateFlow(),
        _searchTerm.asStateFlow(),
    ) { shoppingLists, searchTerm ->

        val filteredLists =
            shoppingLists.filter {
                it.listTitle.contains(
                    other = searchTerm,
                    ignoreCase = true
                )
            }

        ShoppingListScreenState(
            shoppingLists = filteredLists.toImmutableList(),
            search = searchTerm,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShoppingListScreenState()
    )

    fun onAddShoppingList() {
        viewModelScope.launch {
            _sideEffect.emit(
                ShoppingListsSideEffect.NavigateToCreateShoppingListScreen
            )
        }
    }

    fun onShoppingListDetails(listId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(
                ShoppingListsSideEffect.NavigateToShoppingListDetailsScreen(listId)
            )
        }
    }

    fun onSearchTermChange(searchTerm: String) {
        _searchTerm.value = searchTerm.trim()
    }

    fun onSearchFieldClear() {
        _searchTerm.value = ""
    }
}