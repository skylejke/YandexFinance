package ru.point.transactions.expenses.domain.model

import ru.point.api.model.TransactionDto

data class Expense(
    val id: Int,
    val title: String,
    val subtitle: String?,
    val emojiIcon: String?,
    val amount: String,
    val currency: String
)

val TransactionDto.asExpense
    get() = Expense(
        id = id,
        title = category.name,
        subtitle = comment,
        emojiIcon = category.emoji,
        amount = amount,
        currency = account.currency
    )
