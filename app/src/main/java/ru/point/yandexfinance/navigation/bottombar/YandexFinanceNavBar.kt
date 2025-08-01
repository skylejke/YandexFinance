package ru.point.yandexfinance.navigation.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource

@Composable
fun YandexFinanceNavBar(
    entryPoints: List<BottomBarItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        entryPoints.forEachIndexed { index, item ->
            val selected = index == selectedItemIndex
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemSelected(index)
                },
                label = {
                    Text(
                        text = stringResource(item.titleResId),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.iconResId),
                        contentDescription = stringResource(item.titleResId)
                    )
                },
            )
        }
    }
}