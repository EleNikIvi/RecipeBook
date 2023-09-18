package com.okrama.recipesbook.core.flow

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet

class SaveableStateFlow<T> private constructor(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    initialValue: T
) {
    private val backingFlow = MutableStateFlow(savedStateHandle[key] ?: initialValue)

    var value: T
        get() = backingFlow.value
        set(value) {
            backingFlow.value = value
            savedStateHandle[key] = value
        }

    fun update(mutate: (T) -> T) {
        backingFlow.updateAndGet(mutate).also { savedStateHandle[key] = it }
    }

    fun asStateFlow() = backingFlow.asStateFlow()

    companion object {

        fun <T> SavedStateHandle.saveableStateFlow(
            key: String,
            initialValue: T
        ): SaveableStateFlow<T> {
            return SaveableStateFlow(this, key, initialValue)
        }
    }
}