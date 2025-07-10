package ru.point.transactions.ui.editor.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.CategoryIcon
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.vo.CategoryVo

@Composable
internal fun CategoryPickerDialog(
    title: String,
    categories: List<CategoryVo>,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier
        ) {

            Column {

                Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = categories, key = { it.id }) {

                        BaseListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(),
                                    onClick = {
                                        onConfirm(it.name)
                                    }
                                )
                                .padding(horizontal = 16.dp),
                            content = {
                                Text(
                                    text = it.name
                                )
                            },
                            lead = {
                                CategoryIcon(emojiIcon = it.emoji)
                            }
                        )

                        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}