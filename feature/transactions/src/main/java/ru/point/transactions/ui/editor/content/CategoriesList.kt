package ru.point.transactions.ui.editor.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.CategoryIcon
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.vo.CategoryVo

@Composable
internal fun CategoriesList(
    categoriesList: List<CategoryVo>,
    onItemClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
    ) {
        items(items = categoriesList, key = { it.id }) {

            BaseListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            onItemClick(it.id, it.name)
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