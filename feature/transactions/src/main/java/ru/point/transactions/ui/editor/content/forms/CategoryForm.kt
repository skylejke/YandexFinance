package ru.point.transactions.ui.editor.content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.transactions.R
import ru.point.ui.colors.GhostGray
import ru.point.ui.colors.Graphite
import ru.point.ui.composables.BaseListItem

@Composable
internal fun CategoryForm(
    categoryName: String,
    modifier: Modifier = Modifier,
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        }, trail = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Graphite
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.more_icon),
                    contentDescription = null,
                    tint = GhostGray
                )
            }
        }
    )
}