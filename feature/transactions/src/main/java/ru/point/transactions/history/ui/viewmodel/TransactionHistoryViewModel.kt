package ru.point.transactions.history.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.transactions.history.domain.usecase.GetTransactionsHistoryUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject

/**
 * ViewModel, управляющая состоянием истории транзакций за выбранный тип операции (доходы или расходы).
 *
 * Выполняет вызов [GetTransactionsHistoryUseCase] с учётом флага `isIncome` и обновляет UI-состояние
 * с использованием паттерна MVI.
 *
 */
class TransactionHistoryViewModel @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
) : MviViewModel<TransactionHistoryState, TransactionHistoryAction, Any>(initialState = TransactionHistoryState()) {

    private var isIncome: Boolean = true

    fun setIsIncome(income: Boolean) {
        isIncome = income
        loadTransactionsHistory()
    }

    override fun reduce(action: TransactionHistoryAction, state: TransactionHistoryState): TransactionHistoryState {
        return when (action) {

            is TransactionHistoryAction.LoadRequested -> state.copy(isLoading = true, error = null)

            is TransactionHistoryAction.LoadSuccess -> state.copy(
                isLoading = false,
                transactionsHistory = action.transactions
            )

            is TransactionHistoryAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun loadTransactionsHistory() {
        viewModelScope.launch {
            onAction(TransactionHistoryAction.LoadRequested)

            getTransactionsHistoryUseCase(isIncome).fold(
                onSuccess = { transactions ->
                    onAction(TransactionHistoryAction.LoadSuccess(transactions = transactions))
                },
                onFailure = { error ->
                    onAction(TransactionHistoryAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}