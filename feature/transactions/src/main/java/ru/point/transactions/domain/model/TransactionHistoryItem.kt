package ru.point.transactions.domain.model

import ru.point.api.model.TransactionResponseDto

internal data class TransactionHistoryItem(
    val id: Int,
    val title: String,
    val subtitle: String?,
    val emojiIcon: String,
    val amount: String,
    val currency: String,
    val transactionDate: String
)

internal val TransactionResponseDto.asTransactionHistoryItem
    get() = TransactionHistoryItem(
        id = id,
        title = category.name,
        subtitle = comment,
        emojiIcon = category.emoji,
        amount = amount,
        currency = account.currency,
        transactionDate = transactionDate
    )
