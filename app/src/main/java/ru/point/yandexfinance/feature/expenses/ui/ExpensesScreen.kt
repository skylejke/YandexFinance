package ru.point.yandexfinance.feature.expenses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.extensions.toAmountInt
import ru.point.yandexfinance.core.common.extensions.toFormattedCurrency
import ru.point.yandexfinance.core.common.ui.BaseListItem
import ru.point.yandexfinance.core.common.ui.CategoryIcon
import ru.point.yandexfinance.core.common.ui.GreyHorizontalDivider
import ru.point.yandexfinance.core.common.ui.TotalToday
import ru.point.yandexfinance.feature.expenses.model.Expense
import ru.point.yandexfinance.ui.theme.CharcoalGrey
import ru.point.yandexfinance.ui.theme.GhostGray
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.Mint

private val expenses = listOf(
    Expense(
        id = "1",
        title = "Аренда квартиры",
        subtitle = null,
        emojiIcon = "🏡",
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "2",
        title = "Одежда",
        subtitle = null,
        emojiIcon = "👗",
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "3",
        title = "На собачку",
        subtitle = "Джек",
        emojiIcon = "🐶",
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "4",
        title = "На собачку",
        subtitle = "Энни",
        emojiIcon = "🐶",
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "5",
        title = "Ремонт квартиры",
        subtitle = null,
        emojiIcon = null,
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "6",
        title = "Продукты",
        subtitle = null,
        emojiIcon = "🍭",
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "7",
        title = "Спортзал",
        subtitle = null,
        emojiIcon = "🏋️",
        amount = "100000",
        currency = "₽"
    ),
    Expense(
        id = "8",
        title = "Медицина",
        subtitle = null,
        emojiIcon = "💊",
        amount = "100000",
        currency = "₽"
    )
)

private val totalExpensesSum = expenses.sumOf { it.amount.toAmountInt() }.toFormattedCurrency("₽")

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier) {
    ExpensesList(modifier = modifier)
}

@Composable
internal fun ExpensesList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            TotalToday(
                sum = totalExpensesSum,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = Mint)
                    .padding(horizontal = 16.dp)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = expenses, key = { it.id }) { expense ->
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