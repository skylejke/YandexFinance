package ru.point.account.ui.update.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.core.res.account.R
import ru.point.ui.colors.GhostGray
import ru.point.ui.colors.Graphite

@Composable
internal fun UpdateAccountCardTrailingElement(trailingText: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = trailingText,
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