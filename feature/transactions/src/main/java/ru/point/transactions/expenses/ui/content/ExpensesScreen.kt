package ru.point.transactions.expenses.ui.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.transactions.R
import ru.point.transactions.expenses.ui.viewmodel.ExpensesViewModel
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
 * Экран с расходами пользователя за текущий период.
 *
 * Подключает [ExpensesViewModel], отслеживает состояние загрузки, ошибок и соединения,
 * отображает соответствующий UI (список расходов, лоадер, ошибку или баннер отсутствия интернета).
 */
@Composable
fun ExpensesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(Unit) {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.expenses_today,
            actions = listOf(
                TopAppBarAction(
                    iconResId = R.drawable.history_icon,
                    action = onNavigate
                )
            )
        )
        fabState.value = FabState.Showed(
            icon = Icons.Default.Add,
            action = {}
        )
        bottomBarState.value = BottomBarState.Showed
    }

    val viewModel = viewModel<ExpensesViewModel>(factory = LocalViewModelFactory.current)

    val state by viewModel.state.collectAsStateWithLifecycle()

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
                ExpensesScreenContent(
                    state = state,
                    modifier = modifier
                )
            }
        }
    }
}