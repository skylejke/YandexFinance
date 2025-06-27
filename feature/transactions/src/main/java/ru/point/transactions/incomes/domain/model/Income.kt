package ru.point.transactions.incomes.domain.model

import ru.point.api.model.TransactionDto

data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)

val TransactionDto.asIncome
    get() = Income(
        id = id,
        title = category.name,
        amount = amount,
        currency = account.currency
    )
