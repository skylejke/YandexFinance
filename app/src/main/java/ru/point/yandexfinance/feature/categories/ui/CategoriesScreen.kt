package ru.point.yandexfinance.feature.categories.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.ui.composables.BaseListItem
import ru.point.yandexfinance.core.common.ui.composables.CategoryIcon
import ru.point.yandexfinance.core.common.ui.composables.GreyHorizontalDivider
import ru.point.yandexfinance.core.common.ui.composables.NoInternetBanner
import ru.point.yandexfinance.core.common.utils.InternetHolder
import ru.point.yandexfinance.feature.categories.ui.viewmodel.CategoriesAction
import ru.point.yandexfinance.feature.categories.ui.viewmodel.CategoriesState
import ru.point.yandexfinance.feature.categories.ui.viewmodel.CategoriesViewModel
import ru.point.yandexfinance.ui.theme.CharcoalGrey
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.LavenderMist
import ru.point.yandexfinance.ui.theme.Mint

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {

    val viewModel = viewModel<CategoriesViewModel>()

    val state by viewModel.state.collectAsState()

    val tracker = remember { InternetHolder.tracker }

    val isOnline by tracker.online.collectAsState()

    if (!isOnline) {
        NoInternetBanner(
            modifier = Modifier.fillMaxSize(),
            internetTracker = tracker
        )
        return
    }

    when {
        state.isLoading -> {
            Text(
                text = "Загрузка...",
                modifier = Modifier.padding(16.dp)
            )
        }

        state.error != null -> {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            CategoriesList(
                state = state,
                onAction = viewModel::onAction,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CategoriesList(
    state: CategoriesState,
    onAction: (CategoriesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item(key = R.string.search_category) {
            SearchTextField(
                query = state.query,
                onQueryChange = {
                    onAction(CategoriesAction.QueryChanged(it))
                },
                placeHolderResId = R.string.search_category,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(LavenderMist)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = state.categories, key = { it.id }) {
            BaseListItem(
                content = {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Graphite
                    )
                },
                lead = {
                    CategoryIcon(
                        title = it.name,
                        emojiIcon = it.emoji,
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