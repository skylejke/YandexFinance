package ru.point.transactions.history.ui.viewmodel

import ru.point.transactions.history.domain.model.TransactionHistoryItem
import ru.point.utils.model.AppError

sealed interface TransactionHistoryAction {
    data object LoadRequested : TransactionHistoryAction
    data class LoadSuccess(val transactions: List<TransactionHistoryItem>) : TransactionHistoryAction
    data class LoadError(val error: AppError) : TransactionHistoryAction
}