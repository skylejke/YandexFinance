package ru.point.yandexfinance.core.common.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.ui.theme.Mint

@Composable
fun TransactionHistoryBrief(
    startDate: String,
    endDate: String,
    amount: String,
    modifier: Modifier = Modifier
) {

    val baseModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)
        .background(Mint)
        .padding(horizontal = 16.dp)

    Column(modifier = modifier) {
        BaseListItem(
            content = {
                Text(
                    text = "Начало"
                )
            },
            trail = {
                Text(
                    text = startDate
                )
            },
            modifier = baseModifier
        )
        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        BaseListItem(
            content = {
                Text(
                    text = "Конец"
                )
            },
            trail = {
                Text(
                    text = endDate
                )
            },
            modifier = baseModifier
        )
        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        BaseListItem(
            content = {
                Text(
                    text = "Сумма"
                )
            },
            trail = {
                Text(
                    text = amount
                )
            },
            modifier = baseModifier
        )
        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}