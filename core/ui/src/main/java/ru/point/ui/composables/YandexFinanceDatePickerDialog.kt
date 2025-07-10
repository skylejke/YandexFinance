package ru.point.ui.composables

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.point.core.res.common.R
import ru.point.ui.colors.ForestGreen
import ru.point.ui.colors.Graphite
import ru.point.ui.colors.MintGreen
import ru.point.ui.colors.White
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexFinanceDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
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
                    color = ForestGreen
                )

            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = ForestGreen
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                currentYearContentColor = MintGreen,
                selectedYearContentColor = Graphite,
                selectedYearContainerColor = MintGreen,
                selectedDayContainerColor = MintGreen,
                todayContentColor = MintGreen,
                todayDateBorderColor = MintGreen,
                dateTextFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ForestGreen,
                    cursorColor = ForestGreen,
                    focusedLabelColor = ForestGreen,
                    selectionColors = TextSelectionColors(
                        handleColor = ForestGreen,
                        backgroundColor = White
                    ),
                )
            )
        )
    }
}