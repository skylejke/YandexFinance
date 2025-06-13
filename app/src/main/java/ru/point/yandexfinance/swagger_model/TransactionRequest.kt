package ru.point.yandexfinance.swagger_model

data class TransactionRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)