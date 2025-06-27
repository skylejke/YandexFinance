package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    @SerialName("id") val id: Int,
    @SerialName("account") val account: AccountBriefDto,
    @SerialName("category") val category: CategoryDto,
    @SerialName("amount") val amount: String,
    @SerialName("transactionDate") val transactionDate: String,
    @SerialName("comment") val comment: String? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)