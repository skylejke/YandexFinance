package ru.point.yandexfinance.feature.transactions.ui.history.viewmodel

import ru.point.yandexfinance.feature.transactions.model.TransactionHistoryItem

data class TransactionHistoryState(
    val incomesHistory: List<TransactionHistoryItem> = emptyList<TransactionHistoryItem>(),
    val isLoading: Boolean = false,
    val error: String? = null
)
