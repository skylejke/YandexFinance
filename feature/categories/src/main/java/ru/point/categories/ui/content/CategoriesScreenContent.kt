package ru.point.categories.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.categories.ui.viewmodel.CategoriesAction
import ru.point.categories.ui.viewmodel.CategoriesState
import ru.point.core.res.categories.R
import ru.point.ui.colors.LavenderMist
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.utils.extensions.initials

@Composable
internal fun CategoriesScreenContent(
    state: CategoriesState,
    onAction: (CategoriesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item(key = R.string.search_category) {
            SearchTextField(
                query = state.query,
                onQueryChange = {
                    onAction(CategoriesAction.SearchQueryChanged(it))
                },
                placeHolderResId = R.string.search_category,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(LavenderMist)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        if (state.categories.isEmpty()) {
            item(key = R.string.nothing_found) {
                NothingFound(
                    modifier = modifier.padding(top = 16.dp)
                )
            }
        } else {
            items(items = state.categories, key = { it.id }) {

                CategoryCard(
                    contentText = it.name,
                    emojiIcon = it.emoji ?: it.name.initials(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 16.dp)
                )

                GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}