package ru.point.yandexfinance.feature.transactions.model

import ru.point.yandexfinance.core.data.model.TransactionResponse

data class Expense(
    val id: Int,
    val title: String,
    val subtitle: String?,
    val emojiIcon: String?,
    val amount: String,
    val currency: String
)

val TransactionResponse.toExpense
    get() = Expense(
        id = id,
        title = category.name,
        subtitle = comment,
        emojiIcon = category.emoji,
        amount = amount,
        currency = account.currency
    )
