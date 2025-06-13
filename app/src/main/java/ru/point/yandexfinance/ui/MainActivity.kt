package ru.point.yandexfinance.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.Route
import ru.point.yandexfinance.core.common.ui.scaffold.fab.FabState
import ru.point.yandexfinance.core.common.ui.scaffold.fab.YandexFinanceFab
import ru.point.yandexfinance.core.common.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.core.common.ui.scaffold.topappbar.YandexFinanceTopAppBar
import ru.point.yandexfinance.navigation.YandexFinanceNavHost
import ru.point.yandexfinance.navigation.bottombar.BottomBarItem
import ru.point.yandexfinance.navigation.bottombar.YandexFinanceNavBar
import ru.point.yandexfinance.ui.theme.YandexFinanceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val selectedItemIndex = rememberSaveable { mutableIntStateOf(0) }
            val topAppBarState = remember { mutableStateOf<TopAppBarState>(TopAppBarState(R.string.app_name)) }
            val fabState = remember { mutableStateOf<FabState>(FabState.Hidden) }

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
                        YandexFinanceNavBar(
                            entryPoints = BottomBarItem.entryPoints,
                            selectedItemIndex = selectedItemIndex.intValue,
                            onItemSelected = { index ->
                                if (selectedItemIndex.intValue != index) {
                                    selectedItemIndex.intValue = index
                                    navController.navigate(BottomBarItem.entryPoints[index].route)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                        )
                    },
                    floatingActionButton = {
                        when (fabState.value) {
                            is FabState.Showed -> {
                                YandexFinanceFab(
                                    icon = Icons.Default.Add,
                                    onClick = {}
                                )
                            }

                            FabState.Hidden -> {}
                        }
                    }
                ) { innerPadding ->
                    YandexFinanceNavHost(
                        navHostController = navController,
                        startDestination = Route.Expenses,
                        topAppBarState = topAppBarState,
                        fabState = fabState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}