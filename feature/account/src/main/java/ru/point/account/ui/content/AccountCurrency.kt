package ru.point.account.ui.content

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.account.R
import ru.point.ui.colors.Graphite
import ru.point.ui.composables.BaseListItem

@Composable
fun AccountCurrency(
    trailingText: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.currency),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        },
        trail = {
            AccountCardTrailingElement(trailingText = trailingText)
        }
    )
}