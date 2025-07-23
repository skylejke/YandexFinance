package ru.point.yandexfinance.navigation

import kotlinx.serialization.Serializable
import ru.point.navigation.NavigationRoute

sealed interface ComposeNavigationRoute {

    sealed interface TransactionsFeature : ComposeNavigationRoute {
        @Serializable
        data object Expenses : TransactionsFeature

        @Serializable
        data object Incomes : TransactionsFeature

        @Serializable
        data class TransactionHistory(val isIncome: Boolean) : TransactionsFeature

        @Serializable
        data class TransactionEditor(val transactionId: Int?, val isIncome: Boolean) :
            TransactionsFeature

        @Serializable
        data class Analysis(val isIncome: Boolean) : TransactionsFeature
    }

    sealed interface AccountFeature : ComposeNavigationRoute {
        @Serializable
        data object Account : AccountFeature

        @Serializable
        data object EditAccount : AccountFeature
    }


    sealed interface CategoriesFeature : ComposeNavigationRoute {
        @Serializable
        data object Categories : CategoriesFeature
    }

    sealed interface SettingsFeature : ComposeNavigationRoute {
        @Serializable
        data object Settings : SettingsFeature
    }
}

val NavigationRoute.asComposeNavigationRoute
    get() = when (this) {
        is NavigationRoute.AccountFeature.Account -> ComposeNavigationRoute.AccountFeature.Account

        is NavigationRoute.AccountFeature.EditAccount -> ComposeNavigationRoute.AccountFeature.EditAccount

        is NavigationRoute.CategoriesFeature.Categories -> ComposeNavigationRoute.CategoriesFeature.Categories

        is NavigationRoute.SettingsFeature.Settings -> ComposeNavigationRoute.SettingsFeature.Settings

        is NavigationRoute.TransactionsFeature.Analysis -> ComposeNavigationRoute.TransactionsFeature.Analysis(
            isIncome = isIncome
        )

        is NavigationRoute.TransactionsFeature.Expenses -> ComposeNavigationRoute.TransactionsFeature.Expenses

        is NavigationRoute.TransactionsFeature.Incomes -> ComposeNavigationRoute.TransactionsFeature.Incomes

        is NavigationRoute.TransactionsFeature.TransactionEditor -> ComposeNavigationRoute.TransactionsFeature.TransactionEditor(
            transactionId = transactionId,
            isIncome = isIncome
        )

        is NavigationRoute.TransactionsFeature.TransactionHistory -> ComposeNavigationRoute.TransactionsFeature.TransactionHistory(
            isIncome = isIncome
        )
    }