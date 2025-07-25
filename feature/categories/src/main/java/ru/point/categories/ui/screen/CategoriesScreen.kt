package ru.point.categories.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.categories.di.component.DaggerCategoriesComponent
import ru.point.categories.di.deps.CategoriesDepsProvider
import ru.point.categories.ui.content.CategoriesScreenContent
import ru.point.categories.ui.viewmodel.CategoriesViewModel
import ru.point.core.resources.R
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

@NonRestartableComposable
@Composable
fun CategoriesScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    modifier: Modifier = Modifier
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.categories
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Showed

    val categoriesComponent = remember {
        DaggerCategoriesComponent.builder().deps(categoriesDeps = CategoriesDepsProvider.categoriesDeps).build()
    }

    val viewModel = viewModel<CategoriesViewModel>(factory = categoriesComponent.categoriesViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

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
                    CategoriesScreenContent(
                        state = state,
                        onAction = viewModel::onAction,
                        modifier = modifier
                    )
                }
            }
        }
    )
}