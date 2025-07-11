package ru.point.transactions.domain.model

import ru.point.api.model.TransactionResponseDto

internal data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)

internal val TransactionResponseDto.asIncome
    get() = Income(
        id = id,
        title = category.name,
        amount = amount,
        currency = account.currency
    )
