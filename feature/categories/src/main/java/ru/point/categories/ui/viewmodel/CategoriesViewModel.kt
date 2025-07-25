package ru.point.categories.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.point.categories.domain.usecase.GetCategoriesUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject


internal class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : MviViewModel<CategoriesState, CategoriesAction, Any>(initialState = CategoriesState()) {

    init {
        getCategories()
        observeSearchQuery()
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

            is CategoriesAction.FilterCategories -> state.copy(
                isLoading = false,
                categories = action.filterCategories,
                error = null,
            )
        }
    }

    private fun observeSearchQuery() {
        handleAction<CategoriesAction.SearchQueryChanged> {
            val action = getCategoriesUseCase(it.query).fold(
                onSuccess = { filteredCategories ->
                    CategoriesAction.FilterCategories(filteredCategories)
                },
                onFailure = { error ->
                    CategoriesAction.LoadError(error.toAppError())
                }
            )
            onAction(action)
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.Default) {
            onAction(CategoriesAction.LoadRequested)

            getCategoriesUseCase().fold(
                onSuccess = { categories ->
                    onAction(CategoriesAction.LoadSuccess(categories))
                },
                onFailure = { error ->
                    Log.e("ASD", "getCategories: $error")
                    onAction(CategoriesAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}