package com.okrama.recipesbook.ui.addcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okrama.recipesbook.domain.category.CategoryInteractor
import com.okrama.recipesbook.model.EMPTY_CATEGORY_ID
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
class AddCategoryViewModel @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _categoryId =
        savedStateHandle.get<Long>(MainDestinations.CATEGORY_ID_KEY) ?: EMPTY_CATEGORY_ID

    private val _categoryName = savedStateHandle.saveableStateFlow(
        key = "add_category-view-model-name-key",
        initialValue = "",
    )
    private val _isSaved = savedStateHandle.saveableStateFlow(
        key = "add_category-view-model-saved-key",
        initialValue = false,
    )

    val screenState: StateFlow<AddCategoryScreenState> = combine(
        _categoryName.asStateFlow(),
        _isSaved.asStateFlow(),
    ) { categoryName, isSaved ->
        val canSave = categoryName.isNotBlank()

        if (!isSaved) {
            AddCategoryScreenState.Initial(
                title = categoryName,
                canSave = canSave,
            )
        } else {
            AddCategoryScreenState.Saved
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddCategoryScreenState.Initial()
    )

    fun onCategoryNameChange(categoryName: String) {
        _categoryName.value = categoryName
    }

    fun saveCategory() {
        viewModelScope.launch {
            val recipeId = if (_categoryId != EMPTY_CATEGORY_ID) {
                categoryInteractor.updateCategory(
                    id = _categoryId,
                    title = _categoryName.value.trim(),
                )
            } else {
                categoryInteractor.addCategory(
                    title = _categoryName.value.trim(),
                )

            }
            if (recipeId > 0) {
                _isSaved.value = true
            }
        }
    }
}