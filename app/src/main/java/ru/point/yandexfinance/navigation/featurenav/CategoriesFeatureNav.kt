package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.categories.ui.screen.CategoriesScreen
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.ComposeNavigationRoute

fun NavGraphBuilder.categoriesFeature(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<ComposeNavigationRoute.CategoriesFeature.Categories> {
        CategoriesScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            modifier = Modifier.fillMaxSize()
        )
    }
}