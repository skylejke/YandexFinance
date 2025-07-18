package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.TransactionResponseDto
import ru.point.serializable.Category
import ru.point.serializable.asCategoryDto

@Serializable
internal data class TransactionResponse(
    @SerialName("id") val id: Int,
    @SerialName("account") val account: AccountState,
    @SerialName("category") val category: Category,
    @SerialName("amount") val amount: String,
    @SerialName("transactionDate") val transactionDate: String,
    @SerialName("comment") val comment: String? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)

internal val TransactionResponse.asTransactionResponseDto
    get() = TransactionResponseDto(
        id = id,
        account = account.asAccountStateDto,
        category = category.asCategoryDto,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        updatedAt = updatedAt
    )
