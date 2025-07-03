package ru.point.account.domain.model

import ru.point.api.model.AccountDto
import ru.point.utils.extensions.toCurrencySymbol
import ru.point.utils.extensions.toFormattedCurrency

data class AccountVo(
    val name: String,
    val balance: String,
    val currency: String
)

val AccountDto.asAccountVo
    get() = AccountVo(
        name = name,
        balance = balance.toFormattedCurrency(currency.toCurrencySymbol()),
        currency = currency.toCurrencySymbol()
    )

val AccountVo.asAccountDto
    get() = AccountDto(
        name = name,
        balance = balance,
        currency = currency,
    )