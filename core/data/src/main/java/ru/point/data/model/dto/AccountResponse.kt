package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String,
    @SerialName("incomeStats") val incomeStats: StateItemDto,
    @SerialName("expenseStats") val expenseStats: StateItemDto,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)
