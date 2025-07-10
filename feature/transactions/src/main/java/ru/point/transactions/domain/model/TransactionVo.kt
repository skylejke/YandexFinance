package ru.point.transactions.domain.model

import ru.point.api.model.TransactionResponseDto
import ru.point.utils.extensions.toTransactionDate
import ru.point.utils.extensions.toTransactionTime

data class TransactionVo(
    val id: Int,
    val accountName: String,
    val categoryName: String,
    val amount: String,
    val currency: String,
    val transactionDate: String,
    val transactionTime: String,
    val comment: String?
)

val TransactionResponseDto.asTransactionVo
    get() = TransactionVo(
        id = id,
        accountName = account.name,
        categoryName = category.name,
        amount = amount,
        currency = account.currency,
        transactionDate = transactionDate.toTransactionDate(),
        transactionTime = transactionDate.toTransactionTime(),
        comment = comment
    )