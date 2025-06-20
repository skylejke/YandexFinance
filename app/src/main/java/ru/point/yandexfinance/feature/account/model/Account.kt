package ru.point.yandexfinance.feature.account.model

import ru.point.yandexfinance.core.data.model.AccountDto

data class Account(
    val id: Int,
    val balance: String,
    val currency: String
)

val AccountDto.toAccount
    get() = Account(
        id = id,
        balance = balance,
        currency = currency
    )