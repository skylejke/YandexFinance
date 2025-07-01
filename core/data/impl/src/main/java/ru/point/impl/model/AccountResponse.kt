package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AccountResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: String,
    @SerialName("currency") val currency: String,
    @SerialName("incomeStats") val incomeStats: List<StateItem>,
    @SerialName("expenseStats") val expenseStats: List<StateItem>,
)