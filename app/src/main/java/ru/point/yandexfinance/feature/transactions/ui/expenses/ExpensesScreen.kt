package ru.point.yandexfinance.feature.transactions.ui.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.extensions.toAmountInt
import ru.point.yandexfinance.core.common.extensions.toFormattedCurrency
import ru.point.yandexfinance.core.common.ui.composables.BaseListItem
import ru.point.yandexfinance.core.common.ui.composables.CategoryIcon
import ru.point.yandexfinance.core.common.ui.composables.GreyHorizontalDivider
import ru.point.yandexfinance.core.common.ui.composables.NoInternetBanner
import ru.point.yandexfinance.core.common.ui.composables.TotalToday
import ru.point.yandexfinance.core.common.utils.InternetHolder
import ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel.ExpensesAction
import ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel.ExpensesState
import ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel.ExpensesViewModel
import ru.point.yandexfinance.ui.theme.CharcoalGrey
import ru.point.yandexfinance.ui.theme.GhostGray
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.Mint


@Composable
fun ExpensesScreen(modifier: Modifier = Modifier) {

    val viewModel = viewModel<ExpensesViewModel>()

    val state by viewModel.state.collectAsState()

    val tracker = remember { InternetHolder.tracker }

    val isOnline by tracker.online.collectAsState()

    if (!isOnline) {
        NoInternetBanner(
            modifier = Modifier.fillMaxSize(),
            internetTracker = tracker
        )
        return
    }

    when {
        state.isLoading -> {
            Text("Загрузка...", modifier = Modifier.padding(16.dp))
        }

        state.error != null -> {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            ExpensesList(
                state = state,
                onAction = viewModel::onAction,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ExpensesList(
    state: ExpensesState,
    onAction: (ExpensesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        if (state.expenses.isEmpty()) {
            item {
                Text(
                    text = "Нет расходов за сегодня",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Graphite
                )
            }
            return@LazyColumn
        }
        item {
            TotalToday(
                sum = state.expenses.sumOf { it.amount.toAmountInt() }.toFormattedCurrency(state.expenses.first().currency),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = Mint)
                    .padding(horizontal = 16.dp)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = state.expenses, key = { it.id }) { expense ->
            BaseListItem(
                content = {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = expense.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Graphite
                        )
                        if (expense.subtitle != null) {
                            Text(
                                text = expense.subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CharcoalGrey
                            )
                        }
                    }
                },
                lead = {
                    CategoryIcon(
                        title = expense.title,
                        emojiIcon = expense.emojiIcon,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Mint)
                    )
                },
                trail = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = expense.amount.toFormattedCurrency(expense.currency),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Graphite
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.more_icon),
                            contentDescription = null,
                            tint = GhostGray
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}