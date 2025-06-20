package ru.point.yandexfinance.feature.transactions.ui.history.viewmodel

import ru.point.yandexfinance.feature.transactions.model.TransactionHistoryItem

sealed interface TransactionHistoryAction {
    data object LoadRequested : TransactionHistoryAction
    data class LoadSuccess(val incomes: List<TransactionHistoryItem>) : TransactionHistoryAction
    data class LoadError(val message: String) : TransactionHistoryAction
}