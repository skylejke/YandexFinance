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
import ru.point.categories.ui.screen.CategoriesScreen
import ru.point.ui.scaffold.bottombar.BottomBarState

fun NavGraphBuilder.categoriesFeature(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<Route.Categories> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.categories
        )

        fabState.value = FabState.Hidden

        bottomBarState.value = BottomBarState.Showed

        CategoriesScreen(modifier = Modifier.fillMaxSize())
    }
}