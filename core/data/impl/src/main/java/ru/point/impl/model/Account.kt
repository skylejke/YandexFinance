package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.AccountDto

@Serializable
data class Account(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String,
)

val Account.asAccountDto
    get() = AccountDto(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )