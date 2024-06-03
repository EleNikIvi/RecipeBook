package com.okrama.recipesbook.ui.category.addcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
import com.okrama.recipesbook.model.EMPTY_CATEGORY_ID
import com.okrama.recipesbook.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.okrama.recipesbook.ui.core.navigation.RouteKey.CATEGORY_ID_KEY
import com.okrama.recipesbook.ui.recipe.recipes.RecipesSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _categoryId =
        savedStateHandle.get<Long>(CATEGORY_ID_KEY) ?: EMPTY_CATEGORY_ID

    private val _categoryName = savedStateHandle.saveableStateFlow(
        key = "add_category-view-model-name-key",
        initialValue = "",
    )
    private val _savedCategoryId = savedStateHandle.saveableStateFlow(
        key = "add_category-view-model-saved-key",
        initialValue = EMPTY_CATEGORY_ID,
    )

    private val _sideEffect = MutableSharedFlow<AddCategorySideEffect>()
    val sideEffect: SharedFlow<AddCategorySideEffect> = _sideEffect.asSharedFlow()

    val screenState: StateFlow<AddCategoryScreenState> = combine(
        _categoryName.asStateFlow(),
        _savedCategoryId.asStateFlow(),
    ) { categoryName, savedCategoryId ->
        val canSave = categoryName.isNotBlank()

        AddCategoryScreenState(
            title = categoryName,
            canSave = canSave,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddCategoryScreenState()
    )

    fun onCategoryNameChange(categoryName: String) {
        _categoryName.value = categoryName
    }

    fun saveCategory() {
        viewModelScope.launch {
            val categoryId = if (_categoryId != EMPTY_CATEGORY_ID) {
                categoryInteractor.updateCategory(
                    id = _categoryId,
                    title = _categoryName.value.trim(),
                )
            } else {
                categoryInteractor.addCategory(
                    title = _categoryName.value.trim(),
                )

            }
            if (categoryId > 0) {
                _sideEffect.emit(
                    AddCategorySideEffect.OnCategorySaved(categoryId = categoryId)
                )
            }
        }
    }
}