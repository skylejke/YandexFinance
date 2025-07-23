package ru.point.yandexfinance.navigation.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.ComposeNavigationRoute

sealed class BottomBarItem(
    @param:StringRes val titleResId: Int,
    @param:DrawableRes val iconResId: Int,
    val composeNavigationRoute: ComposeNavigationRoute
) {
    data object Expenses : BottomBarItem(
        titleResId = R.string.expenses,
        iconResId = R.drawable.expenses_icon,
        composeNavigationRoute = ComposeNavigationRoute.TransactionsFeature.Expenses
    )

    data object Income : BottomBarItem(
        titleResId = R.string.income,
        iconResId = R.drawable.income_icon,
        composeNavigationRoute = ComposeNavigationRoute.TransactionsFeature.Incomes
    )

    data object Account : BottomBarItem(
        titleResId = R.string.account,
        iconResId = R.drawable.account_icon,
        composeNavigationRoute = ComposeNavigationRoute.AccountFeature.Account
    )

    data object Categories : BottomBarItem(
        titleResId = R.string.categories,
        iconResId = R.drawable.categories_icon,
        composeNavigationRoute = ComposeNavigationRoute.CategoriesFeature.Categories
    )

    data object Settings : BottomBarItem(
        titleResId = R.string.settings,
        iconResId = R.drawable.settings_icon,
        composeNavigationRoute = ComposeNavigationRoute.SettingsFeature.Settings
    )

    companion object {
        val entryPoints = listOf(
            Expenses,
            Income,
            Account,
            Categories,
            Settings
        )
    }
}
