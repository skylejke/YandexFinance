package ru.point.yandexfinance.navigation.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ru.point.ui.colors.Graphite
import ru.point.ui.colors.LavenderWhite
import ru.point.ui.colors.Mint
import ru.point.ui.colors.MintGreen

/**
 * Нижняя навигационная панель приложения YandexFinance.
 *
 * Отображает список пунктов [entryPoints], выделяет активный пункт по [selectedItemIndex]
 * и уведомляет об изменении выбора через [onItemSelected].
 *
 * Использует стилизацию согласно дизайн-системе приложения и Material 3.
 */
@Composable
fun YandexFinanceNavBar(
    entryPoints: List<BottomBarItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = LavenderWhite,
        modifier = modifier
    ) {
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

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MintGreen,
                    indicatorColor = Mint,
                    selectedTextColor = Graphite
                )
            )
        }
    }
}