package ru.point.yandexfinance.core.data.model

data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: StateItemDto,
    val expenseStats: StateItemDto,
    val createdAt: String,
    val updatedAt: String
)
