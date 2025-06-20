package ru.point.yandexfinance.core.data.model

data class AccountStateDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
