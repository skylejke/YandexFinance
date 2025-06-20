package ru.point.yandexfinance.core.data.model

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)