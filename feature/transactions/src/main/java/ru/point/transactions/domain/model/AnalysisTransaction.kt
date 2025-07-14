package ru.point.transactions.domain.model

import ru.point.api.model.TransactionResponseDto

internal data class AnalysisTransaction(
    val id: Int,
    val title: String,
    val comment: String?,
    val emojiIcon: String,
    val amount: String,
    val currency: String,
    val transactionDate: String,
    val part: String,
)

internal fun TransactionResponseDto.asAnalysisTransaction(part: String) =
    AnalysisTransaction(
        id = id,
        title = category.name,
        comment = comment,
        emojiIcon = category.emoji,
        amount = amount,
        currency = account.currency,
        transactionDate = transactionDate,
        part = part
    )