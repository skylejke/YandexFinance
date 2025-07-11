package ru.point.transactions.domain.model

import ru.point.api.model.TransactionResponseDto
import ru.point.utils.extensions.formatAsDate
import ru.point.utils.extensions.formatAsTime

internal data class TransactionVo(
    val id: Int,
    val accountName: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: String,
    val currency: String,
    val transactionDate: String,
    val transactionTime: String,
    val comment: String?
)

internal val TransactionResponseDto.asTransactionVo
    get() = TransactionVo(
        id = id,
        accountName = account.name,
        categoryId = category.id,
        categoryName = category.name,
        amount = amount,
        currency = account.currency,
        transactionDate = transactionDate.formatAsDate(),
        transactionTime = transactionDate.formatAsTime(),
        comment = comment,
    )