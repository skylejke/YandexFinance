package ru.point.settings.ui.content

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ru.point.settings.R
import ru.point.ui.composables.BaseListItem

@Composable
internal fun SettingCard(
    settingTitleResId: Int,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(settingTitleResId),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trail = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.trailing_icon),
                contentDescription = stringResource(settingTitleResId)
            )
        }
    )
}