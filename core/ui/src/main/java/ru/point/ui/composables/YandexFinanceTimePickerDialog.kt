package ru.point.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ru.point.core.res.common.R
import ru.point.ui.colors.ForestGreen
import ru.point.ui.colors.Graphite
import ru.point.ui.colors.MintGreen
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexFinanceTimePickerDialog(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = remember { Calendar.getInstance() }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedTime = LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute
                )
                val formatted = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                onTimeSelected(formatted)
            }) {
                Text(
                    stringResource(R.string.ok),
                    color = ForestGreen
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(R.string.cancel),
                    color = ForestGreen
                )
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialSelectedContentColor = Graphite,
                    timeSelectorSelectedContainerColor = MintGreen,
                    timeSelectorSelectedContentColor = Graphite,
                    timeSelectorUnselectedContentColor = Graphite,
                    selectorColor = ForestGreen
                )
            )
        }
    )
}
