package ru.point.transactions.ui.history.viewmodel

import ru.point.transactions.domain.usecase.GetTransactionsHistoryUseCase
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
internal class TransactionHistoryViewModel @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
    private val isIncome: Boolean
) : MviViewModel<TransactionHistoryState, TransactionHistoryAction, Any>(initialState = TransactionHistoryState()) {

    init {
        onHandleLoadTransactionsHistory()
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

    private fun onHandleLoadTransactionsHistory() {
        handleAction<TransactionHistoryAction.LoadRequested> {
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