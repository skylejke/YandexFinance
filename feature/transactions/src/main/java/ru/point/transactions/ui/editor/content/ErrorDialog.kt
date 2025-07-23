package ru.point.transactions.ui.editor.content

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.point.core.resources.R
import ru.point.ui.colors.ForestGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ErrorDialog(
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
                    stringResource(R.string.try_again),
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
                text = stringResource(R.string.something_went_wrong)
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog(
        onAction = {},
        onDismiss = {},
    )
}