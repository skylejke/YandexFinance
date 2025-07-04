package ru.point.categories.ui.content

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import ru.point.ui.colors.CharcoalGrey
import ru.point.ui.colors.ForestGreen

@Composable
internal fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    @StringRes placeHolderResId: Int,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(placeHolderResId),
                style = MaterialTheme.typography.bodyLarge,
                color = CharcoalGrey,
            )
        },
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = CharcoalGrey
            )
        },
        shape = RectangleShape,
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ForestGreen,
            cursorColor = ForestGreen
        ),
        modifier = modifier
    )
}