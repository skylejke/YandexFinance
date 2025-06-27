package ru.point.transactions.expenses.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.transactions.expenses.domain.usecase.GetExpensesUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject

/**
 * ViewModel, отвечающая за загрузку и управление состоянием экрана с расходами пользователя.
 *
 * Выполняет вызов [GetExpensesUseCase], обрабатывает результат и преобразует его в UI-состояние
 * с использованием MVI-подхода.
 */
class ExpensesViewModel @Inject constructor(private val getExpensesUseCase: GetExpensesUseCase) :
    MviViewModel<ExpensesState, ExpensesAction, Any>(initialState = ExpensesState()) {

    init {
        loadIncomes()
    }

    override fun reduce(action: ExpensesAction, state: ExpensesState): ExpensesState {
        return when (action) {
            is ExpensesAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is ExpensesAction.LoadSuccess -> state.copy(isLoading = false, expenses = action.expenses)
            is ExpensesAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun loadIncomes() {
        viewModelScope.launch {
            onAction(ExpensesAction.LoadRequested)

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