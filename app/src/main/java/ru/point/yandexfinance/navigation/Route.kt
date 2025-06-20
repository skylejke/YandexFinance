package ru.point.yandexfinance.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Expenses : Route

    @Serializable
    data object Incomes : Route

    @Serializable
    data object Account : Route

    @Serializable
    data object Categories : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data class TransactionHistory(val isIncome: Boolean): Route
}