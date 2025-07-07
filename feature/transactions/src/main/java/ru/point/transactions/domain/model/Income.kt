package ru.point.transactions.domain.model

import ru.point.api.model.TransactionDto

internal data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)

internal val TransactionDto.asIncome
    get() = Income(
        id = id,
        title = category.name,
        amount = amount,
        currency = account.currency
    )
