package ru.point.transactions.ui.history.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.navigation.NavigationRoute
import ru.point.transactions.ui.history.viewmodel.TransactionHistoryState
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.ui.composables.NoTransactionsToday
import ru.point.ui.composables.TransactionHistoryBrief

@Composable
internal fun TransactionHistoryScreenContent(
    state: TransactionHistoryState,
    isIncome: Boolean,
    onNavigateToEditor: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.transactionsHistory.isEmpty()) {
        NoTransactionsToday(
            messageResId = R.string.no_transactions,
            modifier = modifier
        )
    } else {
        LazyColumn(modifier = modifier) {
            item {
                TransactionHistoryBrief(
                    endDate = state.transactionsHistory.first().transactionDate,
                    amount = state.amount,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            items(
                items = state.transactionsHistory,
                key = { it.id },
                contentType = { it::class },
            ) { transaction ->
                TransactionsHistoryCard(
                    title = transaction.title,
                    subtitle = transaction.subtitle,
                    emojiIcon = transaction.emojiIcon,
                    amount = transaction.amount,
                    currency = transaction.currency,
                    transactionDate = transaction.transactionDate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(),
                            onClick = {
                                onNavigateToEditor(
                                    NavigationRoute.TransactionsFeature.TransactionEditor(
                                        transaction.id,
                                        isIncome = isIncome
                                    )
                                )
                            }
                        )
                        .padding(horizontal = 16.dp)
                )

                GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}