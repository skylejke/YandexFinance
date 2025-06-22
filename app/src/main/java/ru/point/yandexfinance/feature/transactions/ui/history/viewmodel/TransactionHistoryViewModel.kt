package ru.point.yandexfinance.feature.transactions.ui.history.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.ui.MviViewModel
import ru.point.yandexfinance.feature.transactions.model.toTransactionHistoryItem
import ru.point.yandexfinance.network.RetrofitInstance
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionHistoryViewModel(private val isIncome: Boolean) :
    MviViewModel<TransactionHistoryState, TransactionHistoryAction, Any>(initialState = TransactionHistoryState()) {

    init {
        loadTransactionsHistory()
    }

    override fun reduce(action: TransactionHistoryAction, state: TransactionHistoryState): TransactionHistoryState {
        return when (action) {

            is TransactionHistoryAction.LoadRequested -> state.copy(isLoading = true, error = null)

            is TransactionHistoryAction.LoadSuccess -> state.copy(
                isLoading = false,
                incomesHistory = action.transactions
            )

            is TransactionHistoryAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun loadTransactionsHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            onAction(TransactionHistoryAction.LoadRequested)

            RetrofitInstance.transactionsApi.getTransactionsForPeriod(
                start = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
                end = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            ).fold(
                onSuccess = { incomes ->
                    onAction(
                        TransactionHistoryAction.LoadSuccess(
                            transactions = incomes
                                .filter { it.category.isIncome == isIncome }
                                .sortedByDescending { Instant.parse(it.transactionDate) }
                                .map { it.toTransactionHistoryItem })
                    )
                },
                onFailure = { error ->
                    onAction(TransactionHistoryAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}