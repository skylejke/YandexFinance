package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.point.transactions.ui.analysis.screen.AnalysisScreen
import ru.point.transactions.ui.editor.screen.TransactionEditorScreen
import ru.point.transactions.ui.expenses.screen.ExpensesScreen
import ru.point.transactions.ui.history.screen.TransactionHistoryScreen
import ru.point.transactions.ui.incomes.screen.IncomesScreen
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.ComposeNavigationRoute
import ru.point.yandexfinance.navigation.asComposeNavigationRoute

fun NavGraphBuilder.transactionsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<ComposeNavigationRoute.TransactionsFeature.Incomes> {
        IncomesScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = { navigationRoute -> navController.navigate(navigationRoute.asComposeNavigationRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<ComposeNavigationRoute.TransactionsFeature.Expenses> {
        ExpensesScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = { navigationRoute -> navController.navigate(navigationRoute.asComposeNavigationRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<ComposeNavigationRoute.TransactionsFeature.TransactionHistory> {
        val isIncome =
            it.toRoute<ComposeNavigationRoute.TransactionsFeature.TransactionHistory>().isIncome

        TransactionHistoryScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onBack = navController::popBackStack,
            isIncome = isIncome,
            onNavigate = { navigationRoute -> navController.navigate(navigationRoute.asComposeNavigationRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<ComposeNavigationRoute.TransactionsFeature.TransactionEditor> {
        val isIncome =
            it.toRoute<ComposeNavigationRoute.TransactionsFeature.TransactionEditor>().isIncome
        val transactionId =
            it.toRoute<ComposeNavigationRoute.TransactionsFeature.TransactionEditor>().transactionId
        TransactionEditorScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = navController::navigateUp,
            isIncome = isIncome,
            transactionId = transactionId,
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<ComposeNavigationRoute.TransactionsFeature.Analysis> {
        val isIncome =
            it.toRoute<ComposeNavigationRoute.TransactionsFeature.TransactionHistory>().isIncome
        AnalysisScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onBack = navController::navigateUp,
            isIncome = isIncome,
            modifier = Modifier.fillMaxSize()
        )
    }
}