package ru.point.yandexfinance.feature.expenses.model

internal data class Expense(
    val id: String,
    val title: String,
    val subtitle: String?,
    val emojiIcon: String?,
    val amount: String,
    val currency: String
)
