package ru.point.yandexfinance.core.data.model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
