package ru.point.yandexfinance.swagger_model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
