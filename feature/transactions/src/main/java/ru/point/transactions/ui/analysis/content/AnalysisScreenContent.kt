package ru.point.transactions.ui.analysis.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.transactions.R
import ru.point.transactions.ui.analysis.viewmodel.AnalysisAction
import ru.point.transactions.ui.analysis.viewmodel.AnalysisState
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.ui.composables.NoTransactionsToday
import ru.point.ui.composables.YandexFinanceDatePickerDialog
import ru.point.utils.extensions.toEpochMillis

@Composable
internal fun AnalysisScreenContent(
    state: AnalysisState,
    onAction: (AnalysisAction) -> Unit,
    onNavigateToEditor: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showStartDatePickerDialog by remember { mutableStateOf(false) }
    var showEndDatePickerDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        AnalysisStats(
            state = state,
            onOpenStartDatePicker = { showStartDatePickerDialog = true },
            onOpenEndDatePicker = { showEndDatePickerDialog = true },
            modifier = Modifier.fillMaxWidth(),
        )

        if (state.transactions.isEmpty()) {
            NoTransactionsToday(
                messageResId = R.string.no_transactions,
                modifier = modifier
            )
        } else {
            LazyColumn {
                items(items = state.transactions, key = { it.id }) {
                    AnalysisTransactionCard(
                        emoji = it.emojiIcon,
                        title = it.title,
                        amount = it.amount,
                        currency = it.currency,
                        part = it.part,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(),
                                onClick = {
                                    onNavigateToEditor(it.id)
                                }
                            )
                            .padding(horizontal = 16.dp)
                    )

                    GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

    if (showStartDatePickerDialog) {
        YandexFinanceDatePickerDialog(
            onDateSelected = {
                onAction(AnalysisAction.OnStartMonthChanged(it))
                showStartDatePickerDialog = false
            },
            onDismiss = { showStartDatePickerDialog = false },
            maxDateMillis = state.endDate.toEpochMillis()
        )
    }

    if (showEndDatePickerDialog) {
        YandexFinanceDatePickerDialog(
            onDateSelected = {
                onAction(AnalysisAction.OnEndMonthChanged(it))
                showEndDatePickerDialog = false
            },
            onDismiss = { showEndDatePickerDialog = false },
            minDateMillis = state.startDate.toEpochMillis()
        )
    }
}