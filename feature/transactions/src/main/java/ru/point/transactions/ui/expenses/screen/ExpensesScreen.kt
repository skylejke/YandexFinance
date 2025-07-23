package ru.point.transactions.ui.expenses.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.core.resources.R
import ru.point.navigation.NavigationRoute
import ru.point.transactions.di.component.DaggerExpensesComponent
import ru.point.transactions.di.deps.TransactionDepsStore
import ru.point.transactions.ui.expenses.content.ExpensesScreenContent
import ru.point.transactions.ui.expenses.viewmodel.ExpensesAction
import ru.point.transactions.ui.expenses.viewmodel.ExpensesViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

/**
 * Экран с расходами пользователя за текущий период.
 *
 * Подключает [ExpensesViewModel], отслеживает состояние загрузки, ошибок и соединения,
 * отображает соответствующий UI (список расходов, лоадер, ошибку или баннер отсутствия интернета).
 */
@NonRestartableComposable
@Composable
fun ExpensesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.expenses_today,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.history_icon,
                action = {
                    onNavigate(NavigationRoute.TransactionsFeature.TransactionHistory(isIncome = false))
                }
            )
        )
    )

    fabState.value = FabState.Showed(
        icon = Icons.Default.Add,
        action = {
            onNavigate(
                NavigationRoute.TransactionsFeature.TransactionEditor(
                    transactionId = null,
                    isIncome = false
                )
            )
        }
    )

    bottomBarState.value = BottomBarState.Showed

    val expensesComponent = remember {
        DaggerExpensesComponent.builder()
            .deps(transactionDeps = TransactionDepsStore.transactionDeps).build()
    }

    val viewModel =
        viewModel<ExpensesViewModel>(factory = expensesComponent.expensesViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(ExpensesAction.LoadRequested)
    }

    RequiredInternetContent(
        modifier = modifier,
        content = {
            when {
                state.isLoading -> {
                    LoadingIndicator(modifier = modifier)
                }

                state.error != null -> {
                    ErrorContent(
                        message = state.error!!.toUserMessage(),
                        modifier = modifier
                    )
                }

                else -> {
                    ExpensesScreenContent(
                        state = state,
                        modifier = modifier,
                        onNavigateToEditor = onNavigate
                    )
                }
            }
        },
    )
}