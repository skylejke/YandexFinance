package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountHistoryResponse(
    @SerialName("accountId") val accountId: Int,
    @SerialName("accountName") val accountName: String,
    @SerialName("currency") val currency: String,
    @SerialName("currentBalance") val currentBalance: String,
    @SerialName("history") val history: AccountHistoryDto
)
