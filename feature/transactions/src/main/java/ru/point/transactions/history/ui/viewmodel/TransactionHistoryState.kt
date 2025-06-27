package ru.point.transactions.history.ui.viewmodel

import ru.point.transactions.history.domain.model.TransactionHistoryItem
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError

data class TransactionHistoryState(
    val transactionsHistory: List<TransactionHistoryItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: AppError? = null
) {
    val amount
        get() = transactionsHistory.sumOf { it.amount.toAmountInt() }
            .toFormattedCurrency(transactionsHistory.first().currency)
}
