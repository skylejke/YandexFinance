package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.AccountDto

@Serializable
internal data class AccountUpdateRequest(
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String,
)

internal val AccountDto.asAccountUpdateRequest
    get() = AccountUpdateRequest(
        name = name,
        balance = balance,
        currency = currency,
    )