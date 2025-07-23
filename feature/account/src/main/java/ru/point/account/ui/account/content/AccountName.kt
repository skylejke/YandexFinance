package ru.point.account.ui.account.content

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ru.point.core.resources.R
import ru.point.ui.colors.Graphite
import ru.point.ui.composables.BaseListItem

@Composable
internal fun AccountName(name: String, modifier: Modifier = Modifier) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        },
        lead = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.person_icon),
                contentDescription = name
            )
        },
        trail = {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        }
    )
}