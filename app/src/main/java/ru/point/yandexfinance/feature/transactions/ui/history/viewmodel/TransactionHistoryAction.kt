package ru.point.yandexfinance.feature.transactions.ui.history.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.transactions.model.TransactionHistoryItem

sealed interface TransactionHistoryAction {
    data object LoadRequested : TransactionHistoryAction
    data class LoadSuccess(val transactions: List<TransactionHistoryItem>) : TransactionHistoryAction
    data class LoadError(val error: AppError) : TransactionHistoryAction
}