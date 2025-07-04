package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.AccountDto

@Serializable
internal data class Account(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
)

internal val Account.asAccountDto
    get() = AccountDto(
        name = name,
        balance = balance,
        currency = currency,
    )