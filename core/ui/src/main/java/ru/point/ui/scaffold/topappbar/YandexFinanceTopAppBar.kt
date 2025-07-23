package ru.point.ui.scaffold.topappbar

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
import ru.point.core.resources.R
import ru.point.ui.colors.CharcoalGrey
import ru.point.ui.colors.Graphite
import ru.point.ui.colors.MintGreen
import ru.point.ui.scaffold.action.ActionIcon

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
                ActionIcon(
                    icon = ImageVector.vectorResource(action.iconResId),
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