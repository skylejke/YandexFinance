package ru.point.transactions.ui.analysis.content

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.transactions.R
import ru.point.ui.composables.BaseListItem

@Composable
internal fun AmountCard(amountValue: String, modifier: Modifier = Modifier) {

    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.amount)
            )
        },
        trail = {
            Text(
                text = amountValue
            )
        }
    )
}