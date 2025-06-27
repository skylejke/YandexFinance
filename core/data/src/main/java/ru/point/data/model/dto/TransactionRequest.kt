package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequest(
    @SerialName("accountId") val accountId: Int,
    @SerialName("categoryId") val categoryId: Int,
    @SerialName("amount") val amount: String,
    @SerialName("transactionDate") val transactionDate: String,
    @SerialName("comment") val comment: String? = null
)