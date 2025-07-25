package ru.point.transactions.ui.history.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.transactions.domain.model.TransactionHistoryItem
import ru.point.utils.model.AppError

@Immutable
internal sealed interface TransactionHistoryAction {
    data object LoadRequested : TransactionHistoryAction
    data class LoadSuccess(val transactions: List<TransactionHistoryItem>) : TransactionHistoryAction
    data class LoadError(val error: AppError) : TransactionHistoryAction
}