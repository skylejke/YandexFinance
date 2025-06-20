package ru.point.yandexfinance.feature.transactions.model

import ru.point.yandexfinance.core.data.model.TransactionResponse

data class TransactionHistoryItem(
    val id: Int,
    val title: String,
    val subtitle: String?,
    val emojiIcon: String?,
    val amount: String,
    val currency: String,
    val transactionDate: String
)

val TransactionResponse.toTransactionHistoryItem
    get() = TransactionHistoryItem(
        id = id,
        title = category.name,
        subtitle = comment,
        emojiIcon = category.emoji,
        amount = amount,
        currency = account.currency,
        transactionDate = transactionDate
    )
