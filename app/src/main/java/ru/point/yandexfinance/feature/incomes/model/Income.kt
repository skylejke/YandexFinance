package ru.point.yandexfinance.feature.incomes.model

internal data class Income(
    val id: Int,
    val title: String,
    val amount: String,
    val currency: String
)
