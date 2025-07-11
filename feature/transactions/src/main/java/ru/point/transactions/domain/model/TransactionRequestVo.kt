package ru.point.transactions.domain.model

import ru.point.api.model.TransactionRequestDto

internal data class TransactionRequestVo(
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
)

internal val TransactionRequestVo.asTransactionRequestDto
    get() = TransactionRequestDto(
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
    )

