package ru.point.yandexfinance.core.common.ui.scaffold.topappbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ru.point.yandexfinance.R
import ru.point.yandexfinance.ui.theme.CharcoalGrey
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.MintGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexFinanceTopAppBar(
    topAppBarState: TopAppBarState,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(topAppBarState.titleRes),
                style = MaterialTheme.typography.titleLarge,
                color = Graphite
            )
        },
        actions = {
            topAppBarState.actions.forEach { action ->
                ru.point.yandexfinance.core.common.ui.scaffold.action.ActionIcon(
                    icon = action.icon,
                    action = action.action
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MintGreen,
            titleContentColor = Graphite,
            actionIconContentColor = CharcoalGrey
        ),
        navigationIcon = {
            if (topAppBarState.onBack != null) {
                IconButton(onClick = topAppBarState.onBack::invoke) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.back_icon),
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}