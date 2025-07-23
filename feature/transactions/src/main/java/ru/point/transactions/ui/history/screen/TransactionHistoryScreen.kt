package ru.point.transactions.ui.history.screen

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
import ru.point.transactions.di.component.DaggerTransactionHistoryComponent
import ru.point.transactions.di.deps.TransactionDepsProvider
import ru.point.transactions.ui.history.content.TransactionHistoryScreenContent
import ru.point.transactions.ui.history.viewmodel.TransactionHistoryAction
import ru.point.transactions.ui.history.viewmodel.TransactionHistoryViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

/**
 * Экран истории транзакций пользователя для выбранного типа операций (доходы или расходы).
 *
 * Подключает [TransactionHistoryViewModel], устанавливает флаг [isIncome],
 * отслеживает состояние и отображает соответствующий UI (список, загрузку, ошибку или отсутствие интернета).
 */
@NonRestartableComposable
@Composable
fun TransactionHistoryScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onBack: () -> Unit,
    isIncome: Boolean,
    onNavigateToEditor: (Int?) -> Unit,
    onNavigateToAnalysis: (Boolean)-> Unit,
    modifier: Modifier = Modifier
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.my_history,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.analysis_icon,
                action = {
                    onNavigateToAnalysis(isIncome)
                }
            )
        ),
        onBack = onBack
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Hidden

    val transactionHistoryComponent = remember {
        DaggerTransactionHistoryComponent
            .builder()
            .isIncome(isIncome = isIncome)
            .deps(transactionDeps = TransactionDepsProvider.transactionDeps)
            .build()
    }

    val viewModel: TransactionHistoryViewModel =
        viewModel(factory = transactionHistoryComponent.transactionHistoryViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(TransactionHistoryAction.LoadRequested)
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
                    TransactionHistoryScreenContent(
                        state = state,
                        onNavigateToEditor = onNavigateToEditor,
                        modifier = modifier
                    )
                }
            }
        }
    )
}