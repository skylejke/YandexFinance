package ru.point.transactions.history.domain.model

import ru.point.data.model.dto.TransactionResponse

data class TransactionHistoryItem(
    val id: Int,
    val title: String,
    val subtitle: String?,
    val emojiIcon: String?,
    val amount: String,
    val currency: String,
    val transactionDate: String
)

val TransactionResponse.asTransactionHistoryItem
    get() = TransactionHistoryItem(
        id = id,
        title = category.name,
        subtitle = comment,
        emojiIcon = category.emoji,
        amount = amount,
        currency = account.currency,
        transactionDate = transactionDate
    )
