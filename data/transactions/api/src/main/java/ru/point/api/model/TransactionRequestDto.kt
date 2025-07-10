package ru.point.api.model

data class TransactionRequestDto(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
)