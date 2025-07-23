package ru.point.settings.ui.content

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import ru.point.ui.colors.MintGreen
import ru.point.ui.composables.BaseListItem

@Composable
internal fun SwitchThemeCard(
    isDarkTheme: Boolean,
    switchTheme: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.setting_theme),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trail = {
            Switch(
                checked = isDarkTheme,
                onCheckedChange = switchTheme,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MintGreen
                )
            )
        }
    )
}