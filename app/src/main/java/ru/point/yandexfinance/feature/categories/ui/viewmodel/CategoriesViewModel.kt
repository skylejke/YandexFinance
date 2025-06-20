package ru.point.yandexfinance.feature.categories.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.model.toUserMessage
import ru.point.yandexfinance.feature.categories.model.toDomain
import ru.point.yandexfinance.network.RetrofitInstance

class CategoriesViewModel : ViewModel() {

    private val _state = MutableStateFlow<CategoriesState>(CategoriesState())
    val state get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<CategoriesAction>()
    val action get() = _action.asSharedFlow()

    init {
        viewModelScope.launch {
            _action.collect { action ->
                _state.update { reduce(action, it) }
            }
        }

        getCategories()
    }

    fun onAction(action: CategoriesAction) {
        viewModelScope.launch {
            _action.emit(action)
        }
    }

    private fun reduce(action: CategoriesAction, state: CategoriesState): CategoriesState {
        return when (action) {
            is CategoriesAction.LoadRequested -> state.copy(
                isLoading = true,
                error = null
            )

            is CategoriesAction.LoadCategories -> state.copy(
                isLoading = false,
                categories = action.categories,
                error = null
            )

            is CategoriesAction.QueryChanged -> state.copy(
                query = action.query
            )

            is CategoriesAction.Error -> state.copy(
                isLoading = false,
                error = action.message
            )
        }
    }


    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _action.emit(CategoriesAction.LoadRequested)

            RetrofitInstance.categoriesApi.getCategories().fold(
                onSuccess = { categories ->
                    _action.emit(CategoriesAction.LoadCategories(categories.map { it.toDomain }))
                    Log.d("ASD", "getCategories: $categories")
                },
                onFailure = { error ->
                    _action.emit(CategoriesAction.Error(error.toAppError().toUserMessage()))
                }
            )
        }
    }
}