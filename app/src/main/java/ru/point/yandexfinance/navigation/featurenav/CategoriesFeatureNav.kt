package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.Route
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.categories.ui.content.CategoriesScreen

fun NavGraphBuilder.categoriesFeature(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>
) {
    composable<Route.Categories> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.categories
        )

        fabState.value = FabState.Hidden

        CategoriesScreen(modifier = Modifier.fillMaxSize())
    }
}