package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.point.transactions.expenses.ui.content.ExpensesScreen
import ru.point.transactions.history.ui.content.TransactionHistoryScreen
import ru.point.transactions.incomes.ui.content.IncomesScreen
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.Route

fun NavGraphBuilder.transactionsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<Route.Incomes> {
        IncomesScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = { navController.navigate(Route.TransactionHistory(isIncome = true)) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<Route.Expenses> {
        ExpensesScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = { navController.navigate(Route.TransactionHistory(isIncome = false)) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<Route.TransactionHistory> {
        val isIncome = it.toRoute<Route.TransactionHistory>().isIncome

        TransactionHistoryScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onBack = navController::popBackStack,
            isIncome = isIncome,
            modifier = Modifier.fillMaxSize()
        )
    }
}