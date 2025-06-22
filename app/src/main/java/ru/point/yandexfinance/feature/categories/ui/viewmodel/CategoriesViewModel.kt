package ru.point.yandexfinance.feature.categories.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.ui.MviViewModel
import ru.point.yandexfinance.feature.categories.model.toDomain
import ru.point.yandexfinance.network.RetrofitInstance

class CategoriesViewModel : MviViewModel<CategoriesState, CategoriesAction, Any>(initialState = CategoriesState()) {

    init {
        getCategories()
    }

    override fun reduce(action: CategoriesAction, state: CategoriesState): CategoriesState {
        return when (action) {

            is CategoriesAction.LoadRequested -> state.copy(
                isLoading = true,
                error = null
            )

            is CategoriesAction.LoadSuccess -> state.copy(
                isLoading = false,
                categories = action.categories,
                error = null
            )

            is CategoriesAction.SearchQueryChanged -> state.copy(
                query = action.query
            )

            is CategoriesAction.LoadError -> state.copy(
                isLoading = false,
                error = action.error
            )
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            onAction(CategoriesAction.LoadRequested)

            RetrofitInstance.categoriesApi.getCategories().fold(
                onSuccess = { categories ->
                    onAction(CategoriesAction.LoadSuccess(categories.map { it.toDomain }))
                },
                onFailure = { error ->
                    onAction(CategoriesAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}