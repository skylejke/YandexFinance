package ru.point.transactions.ui.incomes.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.navigation.NavigationRoute
import ru.point.transactions.ui.incomes.viewmodel.IncomesState
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.ui.composables.NoTransactionsToday
import ru.point.ui.composables.TotalToday

@Composable
internal fun IncomesScreenContent(
    state: IncomesState,
    onNavigateToEditor: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.incomes.isEmpty()) {
        NoTransactionsToday(
            messageResId = R.string.no_incomes_today,
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
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = 16.dp)
                )

                GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
            }

            items(
                items = state.incomes,
                key = { it.id },
                contentType = { it::class },
            ) { income ->
                IncomesCard(
                    title = income.title,
                    amount = income.amount,
                    currency = income.currency,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(),
                            onClick = {
                                onNavigateToEditor(
                                    NavigationRoute.TransactionsFeature.TransactionEditor(
                                        income.id,
                                        isIncome = true
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