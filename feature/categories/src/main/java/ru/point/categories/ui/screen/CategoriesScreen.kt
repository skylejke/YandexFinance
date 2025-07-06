package ru.point.categories.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.categories.di.component.DaggerCategoriesComponent
import ru.point.categories.di.deps.CategoriesDepsStore
import ru.point.categories.ui.content.CategoriesScreenContent
import ru.point.categories.ui.viewmodel.CategoriesViewModel
import ru.point.core.res.categories.R
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

/**
 * Экран выбора категорий транзакций.
 *
 * Подключает [CategoriesViewModel], отслеживает состояние подключения, загрузки и ошибок,
 * и отображает соответствующий UI: список категорий, лоадер, ошибку или баннер об отсутствии интернета.
 */
@Composable
fun CategoriesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(Unit) {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.categories
        )

        fabState.value = FabState.Hidden

        bottomBarState.value = BottomBarState.Showed
    }

    val categoriesComponent = remember {
        DaggerCategoriesComponent.builder().deps(categoriesDeps = CategoriesDepsStore.categoriesDeps).build()
    }

    val viewModel = viewModel<CategoriesViewModel>(factory = categoriesComponent.categoriesViewModelFactory)

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