package ru.point.transactions.ui.editor.content.forms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import ru.point.ui.composables.BaseListItem

@Composable
internal fun CommentForm(
    commentValue: String,
    onCommentChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseListItem(
        modifier = modifier,
        content = {
            OutlinedTextField(
                value = commentValue,
                onValueChange = onCommentChanged,
                placeholder = {
                    Text(
                        text = stringResource(R.string.comment),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}