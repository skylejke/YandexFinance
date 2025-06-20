package ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel

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
import ru.point.yandexfinance.feature.transactions.model.toExpense
import ru.point.yandexfinance.network.RetrofitInstance
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExpensesViewModel : ViewModel() {
    private val _state = MutableStateFlow(ExpensesState())
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<ExpensesAction>()
    val actions get() = _actions.asSharedFlow()

    init {
        viewModelScope.launch {
            _actions.collect { action ->
                _state.update { reduce(action, it) }
            }
        }

        loadIncomes()
    }

    fun onAction(action: ExpensesAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun reduce(action: ExpensesAction, state: ExpensesState): ExpensesState {
        return when (action) {
            is ExpensesAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is ExpensesAction.LoadSuccess -> state.copy(isLoading = false, expenses = action.expenses)
            is ExpensesAction.LoadError -> state.copy(isLoading = false, error = action.message)
        }
    }

    private fun loadIncomes() {
        viewModelScope.launch(Dispatchers.IO) {
            _actions.emit(ExpensesAction.LoadRequested)

            RetrofitInstance.transactionsApi.getTransactionsForPeriod(
                accountId = 24,
                start = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                end = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            ).fold(
                onSuccess = { expenses ->
                    _actions.emit(
                        ExpensesAction.LoadSuccess(
                            expenses = expenses
                                .filter { !it.category.isIncome }
                                .map { it.toExpense })
                    )
                },
                onFailure = { error ->
                    _actions.emit(ExpensesAction.LoadError(error.toAppError().toUserMessage()))
                }
            )
        }
    }
}