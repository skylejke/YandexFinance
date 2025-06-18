package ru.point.yandexfinance.feature.categories.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.ui.BaseListItem
import ru.point.yandexfinance.core.common.ui.CategoryIcon
import ru.point.yandexfinance.core.common.ui.GreyHorizontalDivider
import ru.point.yandexfinance.feature.categories.model.Category
import ru.point.yandexfinance.ui.theme.CharcoalGrey
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.LavenderMist
import ru.point.yandexfinance.ui.theme.Mint

private val categories = listOf(
    Category(id = 1, title = "Аренда квартиры", emojiIcon = "🏡"),
    Category(id = 2, title = "Одежда", emojiIcon = "👗"),
    Category(id = 3, title = "На собачку", emojiIcon = "🐶"),
    Category(id = 4, title = "На собачку", emojiIcon = "🐶"),
    Category(id = 5, title = "Ремонт квартиры", emojiIcon = null),
    Category(id = 6, title = "Продукты", emojiIcon = "🍭"),
    Category(id = 7, title = "Спортзал", emojiIcon = "🏋️"),
    Category(id = 8, title = "Медицина", emojiIcon = "💊")
)

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    CategoriesList(
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        modifier = modifier
    )
}

@Composable
private fun CategoriesList(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item(key = R.string.search_category) {
            SearchTextField(
                query = query,
                onQueryChange = onQueryChange,
                placeHolderResId = R.string.search_category,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(LavenderMist)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = categories, key = { it.id }) {
            BaseListItem(
                content = {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Graphite
                    )
                },
                lead = {
                    CategoryIcon(
                        title = it.title,
                        emojiIcon = it.emojiIcon,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Mint),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 16.dp)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun SearchTextField(
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
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField(
        query = "",
        onQueryChange = {},
        placeHolderResId = R.string.search_category,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(LavenderMist)
    )
}