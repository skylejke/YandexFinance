package ru.point.transactions.ui.incomes.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.transactions.R
import ru.point.transactions.di.component.DaggerIncomesComponent
import ru.point.transactions.di.deps.TransactionDepsProvider
import ru.point.transactions.ui.incomes.content.IncomesScreenContent
import ru.point.transactions.ui.incomes.viewmodel.IncomesViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

/**
 * Экран доходов пользователя. Отображает список доходов, либо состояние загрузки, ошибки или отсутствия интернета.
 *
 * Выполняет подписку на [IncomesViewModel], наблюдает за состоянием и отображает соответствующий UI.
 */
@NonRestartableComposable
@Composable
fun IncomesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigateToHistory: () -> Unit,
    onNavigateToEditor: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {


    topAppBarState.value = TopAppBarState(
        titleRes = R.string.incomes_today,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.history_icon,
                action = onNavigateToHistory
            )
        ),
    )

    fabState.value = FabState.Showed(
        icon = Icons.Default.Add,
        action = {
            onNavigateToEditor(null)
        }
    )

    bottomBarState.value = BottomBarState.Showed

    val incomesComponent = remember {
        DaggerIncomesComponent.builder().deps(transactionDeps = TransactionDepsProvider.transactionDeps).build()
    }

    val viewModel = viewModel<IncomesViewModel>(factory = incomesComponent.incomesViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val isOnline by LocalInternetTracker.current.online.collectAsState()

    when {
        isOnline.not() -> NoInternetBanner(modifier = modifier)

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
                onNavigateToEditor = onNavigateToEditor,
                modifier = modifier
            )
        }
    }
}