package ru.point.transactions.ui.editor.content.forms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import ru.point.ui.composables.BaseListItem

@Composable
internal fun DateForm(
    dateValue: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.date),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trail = {
            Text(
                text = dateValue,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    )
}