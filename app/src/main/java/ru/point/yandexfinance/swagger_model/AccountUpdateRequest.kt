package ru.point.yandexfinance.swagger_model

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)