package ru.point.transactions.history.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.transactions.R
import ru.point.transactions.history.ui.viewmodel.TransactionHistoryViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.di.LocalViewModelFactory
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
@Composable
fun TransactionHistoryScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onBack: () -> Unit,
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(Unit) {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.my_history,
            actions = listOf(
                TopAppBarAction(
                    iconResId = R.drawable.analysis_icon,
                    action = {

                    }
                )
            ),
            onBack = onBack
        )

        fabState.value = FabState.Hidden

        bottomBarState.value = BottomBarState.Hidden
    }

    val viewModel: TransactionHistoryViewModel = viewModel(factory = LocalViewModelFactory.current)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(isIncome) {
        // Честно, не знаю, как правильно этот флажок передать, кроме как так
        viewModel.setIsIncome(isIncome)
    }

    val isOnline by LocalInternetTracker.current.online.collectAsState()

    if (!isOnline) {
        NoInternetBanner(modifier = modifier)
    } else {
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
                    modifier = modifier
                )
            }
        }
    }
}