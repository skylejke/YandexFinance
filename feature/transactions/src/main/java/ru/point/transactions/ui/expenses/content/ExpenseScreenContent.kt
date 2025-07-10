package ru.point.transactions.ui.expenses.content

import androidx.compose.foundation.background
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
import ru.point.transactions.R
import ru.point.transactions.ui.expenses.viewmodel.ExpensesState
import ru.point.ui.colors.Mint
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.ui.composables.NoTransactionsToday
import ru.point.ui.composables.TotalToday

@Composable
internal fun ExpensesScreenContent(
    state: ExpensesState,
    onNavigateToEditor: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.expenses.isEmpty()) {
        NoTransactionsToday(
            messageResId = R.string.no_expenses_today,
            modifier = modifier
        )
    } else {
        LazyColumn(modifier = modifier) {
            item {
                TotalToday(
                    sum = state.amount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = Mint)
                        .padding(horizontal = 16.dp)
                )

                GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
            }

            items(items = state.expenses, key = { it.id }) { expense ->
                ExpensesCard(
                    title = expense.title,
                    subtitle = expense.subtitle,
                    emojiIcon = expense.emojiIcon,
                    amount = expense.amount,
                    currency = expense.currency,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(),
                            onClick = {
                                onNavigateToEditor(expense.id)
                            }
                        )
                        .padding(horizontal = 16.dp)
                )

                GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}