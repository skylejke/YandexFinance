package ru.point.account.ui.account.content

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import ru.point.ui.composables.BaseListItem

@Composable
fun AccountCurrency(
    currency: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.currency),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trail = {
            Text(
                text = currency,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    )
}