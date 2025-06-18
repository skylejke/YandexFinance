package ru.point.yandexfinance.feature.incomes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.extensions.toAmountInt
import ru.point.yandexfinance.core.common.extensions.toFormattedCurrency
import ru.point.yandexfinance.core.common.ui.BaseListItem
import ru.point.yandexfinance.core.common.ui.GreyHorizontalDivider
import ru.point.yandexfinance.core.common.ui.TotalToday
import ru.point.yandexfinance.feature.incomes.model.Income
import ru.point.yandexfinance.ui.theme.GhostGray
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.Mint

private val incomes = listOf<Income>(
    Income(id = 1, title = "Зарплата", amount = "500000", currency = "₽"),
    Income(id = 2, title = "Подработка", amount = "100000", currency = "₽")
)

private val totalIncomesSum = incomes.sumOf { it.amount.toAmountInt() }.toFormattedCurrency("₽")

@Composable
fun IncomesScreen(modifier: Modifier = Modifier) {
    IncomesList(modifier)
}

@Composable
private fun IncomesList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            TotalToday(
                sum = totalIncomesSum,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = Mint)
                    .padding(horizontal = 16.dp)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = incomes, key = { it.id }) { income ->
            BaseListItem(
                content = {
                    Text(
                        text = income.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Graphite
                    )
                },
                trail = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = income.amount.toFormattedCurrency(income.currency),
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

