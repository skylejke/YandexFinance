package ru.point.categories.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.categories.ui.content.CategoriesScreenContent
import ru.point.categories.ui.viewmodel.CategoriesViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.di.LocalViewModelFactory
import ru.point.utils.model.toUserMessage

/**
 * Экран выбора категорий транзакций.
 *
 * Подключает [CategoriesViewModel], отслеживает состояние подключения, загрузки и ошибок,
 * и отображает соответствующий UI: список категорий, лоадер, ошибку или баннер об отсутствии интернета.
 */
@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {

    val viewModel = viewModel<CategoriesViewModel>(factory = LocalViewModelFactory.current)

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
                CategoriesScreenContent(
                    state = state,
                    onAction = viewModel::onAction,
                    modifier = modifier
                )
            }
        }
    }
}