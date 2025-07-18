package ru.point.transactions.ui.analysis.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.transactions.R
import ru.point.transactions.ui.analysis.viewmodel.AnalysisState
import ru.point.ui.composables.GreyHorizontalDivider

@Composable
internal fun AnalysisStats(
    state: AnalysisState,
    onOpenStartDatePicker: () -> Unit,
    onOpenEndDatePicker: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        MonthPickerCard(
            contentTextResId = R.string.period_start,
            month = state.startDate,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = onOpenStartDatePicker
                )
                .padding(horizontal = 16.dp)
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        MonthPickerCard(
            contentTextResId = R.string.period_end,
            month = state.endDate,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = onOpenEndDatePicker
                )
                .padding(horizontal = 16.dp)
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        AmountCard(
            amountValue = state.totalAmount,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}