package com.okrama.recipesbook.ui.shoppinglist.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.data.local.entity.Product
import com.okrama.recipesbook.domain.shoppinglist.ShoppingListInteractor
import com.okrama.recipesbook.model.EMPTY_LIST_ID
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.navigation.RouteKey
import com.okrama.recipesbook.ui.shoppinglist.list.ShoppingListsSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListDetailsViewModel @Inject constructor(
    private val shoppingListInteractor: ShoppingListInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _listId =
        savedStateHandle.get<Long>(RouteKey.SHOPPING_LIST_ID_KEY) ?: EMPTY_LIST_ID

    private val _listTitle = savedStateHandle.saveableStateFlow(
        key = "list-details-view-model-title-key",
        initialValue = "",
    )

    private val _initialProducts = MutableStateFlow(emptyList<Product>())
    private val _productModels = savedStateHandle.saveableStateFlow(
        key = "list-details-view-model-title-key",
        initialValue = emptyList<ProductModel>(),
    )

    init {
        viewModelScope.launch {
            if (_listId != EMPTY_LIST_ID) {
                shoppingListInteractor.getShoppingListWithProducts(_listId)
                    .collect { shoppingListWithProducts ->
                        _listTitle.value = shoppingListWithProducts.shoppingList.title
                        _initialProducts.value = shoppingListWithProducts.products
                        _productModels.value = shoppingListWithProducts.products.map {
                            ProductModel(
                                productId = it.productId,
                                productName = it.product,
                                isDone = it.isDone,
                            )
                        }
                    }
            }
        }
    }

    private val _sideEffect = MutableSharedFlow<ShoppingListDetailsSideEffect>()
    val sideEffect: SharedFlow<ShoppingListDetailsSideEffect> = _sideEffect.asSharedFlow()

    val screenState: StateFlow<ShoppingListDetailsScreenState> = combine(
        _listTitle.asStateFlow(),
        _productModels.asStateFlow(),
    ) { title, products ->

        ShoppingListDetailsScreenState(
            listTitle = title,
            products = products.toImmutableList()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShoppingListDetailsScreenState("")
    )

    fun updateProductIsDone(productId: Long, isDone: Boolean) {
        viewModelScope.launch {
            val product = _initialProducts.value.first { it.productId == productId }
            shoppingListInteractor.updateProductIsDone(
                product = product, isDone = isDone
            )
        }
    }

    fun onEditShoppingList() {
        viewModelScope.launch {
            _sideEffect.emit(
                ShoppingListDetailsSideEffect.NavigateToEditShoppingListScreen(listId = _listId)
            )
        }
    }
}