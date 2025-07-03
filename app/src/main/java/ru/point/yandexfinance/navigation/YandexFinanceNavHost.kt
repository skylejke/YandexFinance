package ru.point.yandexfinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.featurenav.accountFeature
import ru.point.yandexfinance.navigation.featurenav.categoriesFeature
import ru.point.yandexfinance.navigation.featurenav.settingsFeature
import ru.point.yandexfinance.navigation.featurenav.transactionsFeature

/**
 * Главный NavHost приложения.
 *
 * Отвечает за инициализацию и конфигурацию графа навигации, включая экраны транзакций, аккаунта,
 * категорий и настроек. Делегирует управление [TopAppBarState] и [FabState] в зависимости от активного экрана.
 *
 * Использует переданный [NavHostController] и стартовый маршрут [startDestination] для навигации.
 */
@Composable
fun YandexFinanceNavHost(
    navHostController: NavHostController,
    startDestination: Route,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        transactionsFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            fabState = fabState
        )

        accountFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            fabState = fabState
        )

        categoriesFeature(
            topAppBarState = topAppBarState,
            fabState = fabState
        )

        settingsFeature(
            topAppBarState = topAppBarState,
            fabState = fabState
        )
    }
}