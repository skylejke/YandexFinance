package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.TransactionDto

@Serializable
internal data class Transaction(
    @SerialName("id") val id: Int,
    @SerialName("account") val account: AccountState,
    @SerialName("category") val category: Category,
    @SerialName("amount") val amount: String,
    @SerialName("transactionDate") val transactionDate: String,
    @SerialName("comment") val comment: String? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)

internal val Transaction.asTransactionDto
    get() = TransactionDto(
        id = id,
        account = account.asAccountDto,
        category = category.asCategoryDto,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
