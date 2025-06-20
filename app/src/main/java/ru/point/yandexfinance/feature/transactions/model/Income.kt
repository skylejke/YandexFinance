package ru.point.yandexfinance.feature.transactions.model

import ru.point.yandexfinance.core.data.model.TransactionResponse

data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)

val TransactionResponse.toIncome
    get() = Income(
        id = id,
        title = category.name,
        amount = amount,
        currency = account.currency
    )
