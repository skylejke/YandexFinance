package ru.point.account.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.account.R
import ru.point.ui.colors.Graphite
import ru.point.ui.composables.BaseListItem

@Composable
internal fun AccountBalance(
    trailingText: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.balance),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        },
        lead = {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "💰",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Graphite
                )
            }
        },
        trail = {
            AccountCardTrailingElement(
                trailingText = trailingText
            )
        }
    )
}