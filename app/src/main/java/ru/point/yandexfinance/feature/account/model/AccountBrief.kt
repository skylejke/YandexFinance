package ru.point.yandexfinance.feature.account.model

data class AccountBrief(
    val id: Int,
    val balance: String,
    val currency: String
)