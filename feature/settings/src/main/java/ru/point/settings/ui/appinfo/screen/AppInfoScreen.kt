package ru.point.settings.ui.appinfo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.core.resources.R
import ru.point.settings.di.component.DaggerAppInfoComponent
import ru.point.settings.di.deps.SettingsDepsProvider
import ru.point.settings.ui.appinfo.viewmodel.AppInfoViewModel
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState

@NonRestartableComposable
@Composable
fun AppInfoScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.app_info,
        onBack = onBack
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Hidden

    val appInfoComponent = remember {
        DaggerAppInfoComponent.builder().deps(settingsDeps = SettingsDepsProvider.settingsDeps)
            .build()
    }

    val viewModel = viewModel<AppInfoViewModel>(factory = appInfoComponent.appInfoViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.app_version, state.appVersion),
                textAlign = TextAlign.Center,
            )

            Text(
                text = stringResource(R.string.last_time_update, state.lastTimeUpdate),
                textAlign = TextAlign.Center,
            )
        }
    }
}