package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.Route
import ru.point.yandexfinance.core.common.ui.scaffold.fab.FabState
import ru.point.yandexfinance.core.common.ui.scaffold.topappbar.TopAppBarAction
import ru.point.yandexfinance.core.common.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.feature.expenses.ui.ExpensesScreen

fun NavGraphBuilder.expensesFeature(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>
) {
    composable<Route.Expenses> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.expenses_today,
            actions = listOf(
                TopAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.history_icon),
                    action = {}
                )
            )
        )

        fabState.value = FabState.Showed(
            icon = Icons.Default.Add,
            action = {}
        )

        ExpensesScreen(modifier = Modifier.fillMaxSize())
    }
}