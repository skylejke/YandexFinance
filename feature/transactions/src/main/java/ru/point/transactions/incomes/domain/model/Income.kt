package ru.point.transactions.incomes.domain.model

import ru.point.data.model.dto.TransactionResponse

data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)

val TransactionResponse.asIncome
    get() = Income(
        id = id,
        title = category.name,
        amount = amount,
        currency = account.currency
    )
