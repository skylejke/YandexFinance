package ru.point.navigation

sealed interface NavigationRoute {

    sealed interface TransactionsFeature : NavigationRoute {
        data object Expenses : TransactionsFeature

        data object Incomes : TransactionsFeature

        data class TransactionHistory(val isIncome: Boolean) : TransactionsFeature

        data class TransactionEditor(val transactionId: Int?, val isIncome: Boolean) :
            TransactionsFeature

        data class Analysis(val isIncome: Boolean) : TransactionsFeature
    }

    sealed interface AccountFeature : NavigationRoute {

        data object Account : AccountFeature

        data object EditAccount : AccountFeature
    }

    sealed interface CategoriesFeature : NavigationRoute {

        data object Categories : CategoriesFeature
    }

    sealed interface SettingsFeature : NavigationRoute {

        data object Settings : SettingsFeature

        data object AppInfo : SettingsFeature
    }
}