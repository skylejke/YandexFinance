package ru.point.categories.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.CategoryIcon

@Composable
internal fun CategoryCard(
    contentText: String,
    emojiIcon: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        content = {
            Text(
                text = contentText,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        lead = {
            CategoryIcon(
                emojiIcon = emojiIcon,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            )
        },
        modifier = modifier
    )
}