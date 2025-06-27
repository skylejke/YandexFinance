package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.Route
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.transactions.expenses.ui.content.ExpensesScreen
import ru.point.transactions.history.ui.content.TransactionHistoryScreen
import ru.point.transactions.incomes.ui.content.IncomesScreen

fun NavGraphBuilder.transactionsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>
) {
    composable<Route.Incomes> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.income_today,
            actions = listOf(
                TopAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.history_icon),
                    action = {
                        navController.navigate(Route.TransactionHistory(isIncome = true))
                    }
                )
            ),
        )

        fabState.value = FabState.Showed(
            icon = Icons.Default.Add,
            action = {

            }
        )

        IncomesScreen(modifier = Modifier.fillMaxSize())
    }

    composable<Route.Expenses> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.expenses_today,
            actions = listOf(
                TopAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.history_icon),
                    action = {
                        navController.navigate(Route.TransactionHistory(isIncome = false))
                    }
                )
            )
        )

        fabState.value = FabState.Showed(
            icon = Icons.Default.Add,
            action = {}
        )

        ExpensesScreen(modifier = Modifier.fillMaxSize())
    }

    composable<Route.TransactionHistory> {
        val isIncome = it.toRoute<Route.TransactionHistory>().isIncome
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.my_history,
            actions = listOf(
                TopAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.analysis_icon),
                    action = {

                    }
                )
            ),
            onBack = {
                navController.popBackStack()
            }
        )

        fabState.value = FabState.Hidden

        TransactionHistoryScreen(isIncome = isIncome, modifier = Modifier.fillMaxSize())
    }
}