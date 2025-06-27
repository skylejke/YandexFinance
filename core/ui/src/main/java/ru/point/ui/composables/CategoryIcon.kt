package ru.point.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.point.ui.colors.Graphite
import ru.point.ui.colors.Mint
import ru.point.utils.extensions.startsWithEmoji

@Composable
fun CategoryIcon(
    emojiIcon: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Mint),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emojiIcon,
            style = if (emojiIcon.startsWithEmoji()) {
                MaterialTheme.typography.bodyLarge
            } else {
                TextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 10.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.sp,
                    color = Graphite
                )
            },
        )
    }
}