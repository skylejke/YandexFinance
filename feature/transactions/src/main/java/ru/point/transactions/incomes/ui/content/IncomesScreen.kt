package ru.point.transactions.incomes.ui.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.transactions.R
import ru.point.transactions.incomes.ui.viewmodel.IncomesViewModel
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
 * Экран доходов пользователя. Отображает список доходов, либо состояние загрузки, ошибки или отсутствия интернета.
 *
 * Выполняет подписку на [IncomesViewModel], наблюдает за состоянием и отображает соответствующий UI.
 */
@Composable
fun IncomesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: ()-> Unit,
    modifier: Modifier = Modifier,
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.incomes_today,
        actions = listOf(
            TopAppBarAction(
                icon = ImageVector.vectorResource(R.drawable.history_icon),
                action = onNavigate
            )
        ),
    )

    fabState.value = FabState.Showed(
        icon = Icons.Default.Add,
        action = {

        }
    )

    bottomBarState.value = BottomBarState.Showed

    val viewModel = viewModel<IncomesViewModel>(factory = LocalViewModelFactory.current)

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
                IncomesScreenContent(
                    state = state,
                    modifier = modifier
                )
            }
        }
    }
}