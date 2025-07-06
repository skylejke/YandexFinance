package ru.point.account.ui.update.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.point.core.res.account.R
import ru.point.ui.colors.ForestGreen
import ru.point.ui.colors.Graphite
import ru.point.ui.colors.White
import ru.point.utils.extensions.sanitizeDecimalInput

@Composable
internal fun EditTextDialog(
    title: String,
    initialValue: String,
    keyBoardType: KeyboardType,
    maxLength: Int,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    showTextCounter: Boolean = false,
) {

    var text by remember { mutableStateOf(initialValue) }

    Dialog(onDismissRequest = onDismiss) {

        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp,
            color = White
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                Text(text = title, style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyBoardType
                    ),
                    value = text,
                    onValueChange = { input ->
                        if (input.length > maxLength + 3 || input.startsWith(" ")) return@OutlinedTextField

                        val sanitized = if (keyBoardType == KeyboardType.Number) {
                            input.sanitizeDecimalInput()
                        } else input

                        val intPart = sanitized.substringBefore('.', sanitized)
                        if (intPart.length <= maxLength) {
                            text = sanitized
                        }
                    },
                    singleLine = true,
                    supportingText = {
                        if (showTextCounter) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                contentAlignment = Alignment.BottomEnd,
                            ) {
                                Text(
                                    text = "${text.length} / $maxLength",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        selectionColors = TextSelectionColors(
                            handleColor = ForestGreen,
                            backgroundColor = White
                        ),
                        unfocusedBorderColor = Graphite,
                        cursorColor = ForestGreen,
                        focusedBorderColor = ForestGreen,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = ForestGreen
                        )
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }

                    TextButton(
                        onClick = { onConfirm(text) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = ForestGreen
                        )
                    ) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            }
        }
    }
}


