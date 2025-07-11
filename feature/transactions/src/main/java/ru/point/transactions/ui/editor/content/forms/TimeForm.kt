package ru.point.transactions.ui.editor.content.forms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.transactions.R
import ru.point.ui.colors.Graphite
import ru.point.ui.composables.BaseListItem

@Composable
internal fun TimeForm(
    timeValue: String,
    modifier: Modifier = Modifier,
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.time),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        },
        trail = {
            Text(
                text = timeValue,
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        }
    )
}