package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.TransactionRequestDto
import ru.point.core.utils.BuildConfig

@Serializable
internal data class TransactionRequest(
    @SerialName("accountId") val accountId: Int,
    @SerialName("categoryId") val categoryId: Int,
    @SerialName("amount") val amount: String,
    @SerialName("transactionDate") val transactionDate: String,
    @SerialName("comment") val comment: String?,
)

internal val TransactionRequestDto.asTransactionRequest
    get() = TransactionRequest(
        accountId = BuildConfig.ACCOUNT_ID.toInt(),
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment?.ifBlank { null },
    )