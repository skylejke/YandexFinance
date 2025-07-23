package ru.point.yandexfinance.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.fab.YandexFinanceFab
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.ui.scaffold.topappbar.YandexFinanceTopAppBar
import ru.point.utils.network.InternetHolder.tracker
import ru.point.yandexfinance.R
import ru.point.yandexfinance.app.appComponent
import ru.point.yandexfinance.navigation.ComposeNavigationRoute
import ru.point.yandexfinance.navigation.YandexFinanceNavHost
import ru.point.yandexfinance.navigation.bottombar.BottomBarItem
import ru.point.yandexfinance.navigation.bottombar.YandexFinanceNavBar
import ru.point.yandexfinance.ui.theme.YandexFinanceTheme
import javax.inject.Inject

/**
 * Главная активити приложения YandexFinance.
 *
 * Отвечает за инициализацию темизации, DI-контекстов,
 * отображение основной Scaffold-структуры (TopAppBar, BottomNavigation, FAB) и навигации через [YandexFinanceNavHost].
 *
 * Также управляет splash-экраном и поддерживает edge-to-edge режим.
 *
 */
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory

    val mainActivityViewModel by viewModels<MainActivityViewModel> { mainActivityViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        installSplashScreen()
        enableEdgeToEdge()
        mainActivityViewModel
        setContent {
            val navController = rememberNavController()
            val selectedItemIndex = rememberSaveable { mutableIntStateOf(0) }
            val topAppBarState = remember { mutableStateOf(TopAppBarState(R.string.app_name)) }
            val fabState = remember { mutableStateOf<FabState>(FabState.Hidden) }
            val bottomBarState = remember { mutableStateOf<BottomBarState>(BottomBarState.Hidden) }

            CompositionLocalProvider(
                LocalInternetTracker provides tracker
            ) {
                YandexFinanceTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            YandexFinanceTopAppBar(
                                topAppBarState = topAppBarState.value,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        bottomBar = {
                            when (bottomBarState.value) {
                                BottomBarState.Hidden -> {}
                                BottomBarState.Showed -> {
                                    YandexFinanceNavBar(
                                        entryPoints = BottomBarItem.entryPoints,
                                        selectedItemIndex = selectedItemIndex.intValue,
                                        onItemSelected = { index ->
                                            if (selectedItemIndex.intValue != index) {
                                                selectedItemIndex.intValue = index
                                                navController.navigate(BottomBarItem.entryPoints[index].composeNavigationRoute)
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        },
                        floatingActionButton = {
                            when (val state = fabState.value) {
                                is FabState.Showed -> {
                                    YandexFinanceFab(
                                        icon = Icons.Default.Add,
                                        onClick = state.action
                                    )
                                }

                                FabState.Hidden -> {}
                            }
                        }
                    ) { innerPadding ->
                        YandexFinanceNavHost(
                            navHostController = navController,
                            startDestination = ComposeNavigationRoute.TransactionsFeature.Expenses,
                            topAppBarState = topAppBarState,
                            fabState = fabState,
                            bottomBarState = bottomBarState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}