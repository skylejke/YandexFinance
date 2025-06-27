package ru.point.account.domain.model

import ru.point.api.model.AccountDto
import ru.point.utils.extensions.toCurrencySymbol
import ru.point.utils.extensions.toFormattedCurrency

data class AccountVo(
    val id: Int,
    val balance: String,
    val currency: String
)

val AccountDto.asAccountVo
    get() = AccountVo(
        id = id,
        balance = balance.toFormattedCurrency(currency.toCurrencySymbol()),
        currency = currency.toCurrencySymbol()
    )