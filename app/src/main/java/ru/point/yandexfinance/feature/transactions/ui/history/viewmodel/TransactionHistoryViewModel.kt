package ru.point.yandexfinance.feature.transactions.ui.history.viewmodel

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
import ru.point.yandexfinance.feature.transactions.model.toTransactionHistoryItem
import ru.point.yandexfinance.network.RetrofitInstance
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionHistoryViewModel(private val isIncome: Boolean) : ViewModel() {
    private val _state = MutableStateFlow(TransactionHistoryState())
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<TransactionHistoryAction>()
    val actions get() = _actions.asSharedFlow()

    init {
        viewModelScope.launch {
            _actions.collect { action ->
                _state.update { reduce(action, it) }
            }
        }

        loadTransactionsHistory()
    }

    fun onAction(action: TransactionHistoryAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun reduce(action: TransactionHistoryAction, state: TransactionHistoryState): TransactionHistoryState {
        return when (action) {
            is TransactionHistoryAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is TransactionHistoryAction.LoadSuccess -> state.copy(isLoading = false, incomesHistory = action.incomes)
            is TransactionHistoryAction.LoadError -> state.copy(isLoading = false, error = action.message)
        }
    }

    private fun loadTransactionsHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _actions.emit(TransactionHistoryAction.LoadRequested)

            RetrofitInstance.transactionsApi.getTransactionsForPeriod(
                start = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
                end = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            ).fold(
                onSuccess = { incomes ->
                    _actions.emit(
                        TransactionHistoryAction.LoadSuccess(
                            incomes = incomes
                                .filter { it.category.isIncome == isIncome }
                                .sortedByDescending { Instant.parse(it.transactionDate) }
                                .map { it.toTransactionHistoryItem })
                    )
                },
                onFailure = { error->
                    _actions.emit(TransactionHistoryAction.LoadError(error.toAppError().toUserMessage()))
                }
            )
        }
    }
}