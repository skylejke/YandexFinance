package ru.point.transactions.domain.model

import ru.point.api.model.AccountDto

internal data class AccountData(
    val accountName: String,
    val currency: String,
)

internal val AccountDto.asAccountData
    get() = AccountData(
        accountName = name,
        currency = currency,
    )
