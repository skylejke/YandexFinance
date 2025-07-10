package ru.point.transactions.ui.editor.content.forms

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.transactions.R
import ru.point.ui.colors.CharcoalGrey
import ru.point.ui.colors.ForestGreen
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
                        color = CharcoalGrey,
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    selectionColors = TextSelectionColors(
                        handleColor = ForestGreen,
                        backgroundColor = MaterialTheme.colorScheme.background
                    ),
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    cursorColor = ForestGreen,
                ),
                singleLine = true,
            )
        }
    )
}