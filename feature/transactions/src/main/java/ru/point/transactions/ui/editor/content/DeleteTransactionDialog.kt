package ru.point.transactions.ui.editor.content

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import ru.point.ui.colors.ForestGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DeleteTransactionDialog(
    onAction: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onAction) {
                Text(
                    stringResource(R.string.yes),
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
            Text(
                text = stringResource(R.string.delete_transaction_title)
            )
        },
        modifier = modifier
    )
}