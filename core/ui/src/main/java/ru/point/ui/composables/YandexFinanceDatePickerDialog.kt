package ru.point.ui.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexFinanceDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    minDateMillis: Long? = null,
    maxDateMillis: Long? = null,
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = null,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val afterMin = minDateMillis?.let { utcTimeMillis >= it } ?: true
                val beforeMax = maxDateMillis?.let { utcTimeMillis <= it } ?: true
                return afterMin && beforeMax
            }
            override fun isSelectableYear(year: Int): Boolean {
                val afterMinYear = minDateMillis
                    ?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).year <= year }
                    ?: true
                val beforeMaxYear = maxDateMillis
                    ?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).year >= year }
                    ?: true
                return afterMinYear && beforeMaxYear
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val formattedDate = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

                    onDateSelected(formattedDate)
                }
                onDismiss()
            }) {
                Text(
                    text = stringResource(R.string.ok),
                )

            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}