package ru.point.transactions.ui.incomes.screen

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
import ru.point.transactions.di.component.DaggerIncomesComponent
import ru.point.transactions.di.deps.TransactionDepsProvider
import ru.point.transactions.ui.incomes.content.IncomesScreenContent
import ru.point.transactions.ui.incomes.viewmodel.IncomesAction
import ru.point.transactions.ui.incomes.viewmodel.IncomesViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

@NonRestartableComposable
@Composable
fun IncomesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier,
) {


    topAppBarState.value = TopAppBarState(
        titleRes = R.string.incomes_today,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.history_icon,
                action = {
                    onNavigate(NavigationRoute.TransactionsFeature.TransactionHistory(isIncome = true))
                }
            )
        ),
    )

    fabState.value = FabState.Showed(
        icon = Icons.Default.Add,
        action = {
            onNavigate(
                NavigationRoute.TransactionsFeature.TransactionEditor(
                    transactionId = null,
                    isIncome = true
                )
            )
        }
    )

    bottomBarState.value = BottomBarState.Showed

    val incomesComponent = remember {
        DaggerIncomesComponent.builder()
            .deps(transactionDeps = TransactionDepsProvider.transactionDeps).build()
    }

    val viewModel = viewModel<IncomesViewModel>(factory = incomesComponent.incomesViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(IncomesAction.LoadRequested)
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
                    IncomesScreenContent(
                        state = state,
                        onNavigateToEditor = onNavigate,
                        modifier = modifier
                    )
                }
            }
        }
    )
}