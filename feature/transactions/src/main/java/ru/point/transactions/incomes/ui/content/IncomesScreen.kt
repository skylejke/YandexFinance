package ru.point.transactions.incomes.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.transactions.incomes.ui.viewmodel.IncomesViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.di.LocalViewModelFactory
import ru.point.utils.model.toUserMessage

/**
 * Экран доходов пользователя. Отображает список доходов, либо состояние загрузки, ошибки или отсутствия интернета.
 *
 * Выполняет подписку на [IncomesViewModel], наблюдает за состоянием и отображает соответствующий UI.
 */
@Composable
fun IncomesScreen(modifier: Modifier = Modifier) {

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