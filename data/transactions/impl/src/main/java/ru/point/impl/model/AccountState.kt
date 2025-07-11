package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.AccountStateDto

@Serializable
internal data class AccountState(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String,
)

internal val AccountState.asAccountStateDto
    get() = AccountStateDto(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
    )