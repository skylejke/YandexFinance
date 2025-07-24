package ru.point.transactions.domain.model

import androidx.compose.runtime.Immutable
import ru.point.api.model.TransactionResponseDto

@Immutable
internal data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)

internal val TransactionResponseDto.asIncome
    get() = Income(
        id = id,
        title = category.name,
        amount = amount,
        currency = account.currency
    )
