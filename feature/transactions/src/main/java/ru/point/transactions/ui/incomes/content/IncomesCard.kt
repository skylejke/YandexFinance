package ru.point.transactions.ui.incomes.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.TransactionCardContent
import ru.point.ui.composables.TransactionsCardTrail

@Composable
internal fun IncomesCard(
    title: String,
    amount: String,
    currency: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        content = {
            TransactionCardContent(title = title)
        },
        trail = {
            TransactionsCardTrail(
                amount = amount,
                currency = currency
            )
        },
        modifier = modifier
    )
}