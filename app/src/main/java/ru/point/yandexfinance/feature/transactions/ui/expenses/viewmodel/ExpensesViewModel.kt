package ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.ui.MviViewModel
import ru.point.yandexfinance.feature.transactions.model.toExpense
import ru.point.yandexfinance.network.RetrofitInstance
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExpensesViewModel : MviViewModel<ExpensesState, ExpensesAction, Any>(initialState = ExpensesState()) {

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
        viewModelScope.launch(Dispatchers.IO) {
            onAction(ExpensesAction.LoadRequested)

            RetrofitInstance.transactionsApi.getTransactionsForPeriod(
                start = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                end = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            ).fold(
                onSuccess = { expenses ->
                    onAction(
                        ExpensesAction.LoadSuccess(
                            expenses = expenses
                                .filter { !it.category.isIncome }
                                .map { it.toExpense })
                    )
                },
                onFailure = { error ->
                    onAction(ExpensesAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}