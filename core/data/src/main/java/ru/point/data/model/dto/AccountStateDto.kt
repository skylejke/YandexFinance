package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountStateDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String
)
