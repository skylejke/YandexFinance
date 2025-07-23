package ru.point.transactions.ui.expenses.viewmodel

import ru.point.transactions.domain.usecase.GetExpensesUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject


internal class ExpensesViewModel @Inject constructor(private val getExpensesUseCase: GetExpensesUseCase) :
    MviViewModel<ExpensesState, ExpensesAction, Any>(initialState = ExpensesState()) {

    init {
        onHandeLoadExpenses()
    }

    override fun reduce(action: ExpensesAction, state: ExpensesState): ExpensesState {
        return when (action) {
            is ExpensesAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is ExpensesAction.LoadSuccess -> state.copy(isLoading = false, expenses = action.expenses)
            is ExpensesAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun onHandeLoadExpenses() {
        handleAction<ExpensesAction.LoadRequested> {
            getExpensesUseCase().fold(
                onSuccess = { expenses ->
                    onAction(ExpensesAction.LoadSuccess(expenses = expenses))
                },
                onFailure = { error ->
                    onAction(ExpensesAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}