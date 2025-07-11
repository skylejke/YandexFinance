package ru.point.account.ui.update.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.core.res.account.R
import ru.point.ui.colors.White
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.utils.extensions.CurrencyParse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrencyPickerBottomSheet(
    onDismiss: () -> Unit,
    onCurrencySelected: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = White,
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {

            val currencyItemModifier = Modifier
                .fillMaxWidth()
                .height(70.dp)


            CurrencyItem(
                iconResId = R.drawable.ruble_icon,
                titleResId = R.string.ruble,
                modifier = currencyItemModifier
                    .clickable {
                        onCurrencySelected(CurrencyParse.RUB.symbol)
                        onDismiss()
                    }
                    .padding(16.dp)
            )

            CurrencyItem(
                iconResId = R.drawable.dollar_icon,
                titleResId = R.string.dollar,
                modifier = currencyItemModifier
                    .clickable {
                        onCurrencySelected(CurrencyParse.USD.symbol)
                        onDismiss()
                    }
                    .padding(16.dp)
            )

            CurrencyItem(
                iconResId = R.drawable.euro_icon,
                titleResId = R.string.euro,
                modifier = currencyItemModifier
                    .clickable {
                        onCurrencySelected(CurrencyParse.EUR.symbol)
                        onDismiss()
                    }
                    .padding(16.dp)
            )

            CancelItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFE46962))
                    .clickable { onDismiss() }
                    .padding(16.dp),
            )
        }
    }
}

@Composable
internal fun CurrencyItem(
    iconResId: Int,
    titleResId: Int,
    modifier: Modifier = Modifier,
) {
    BaseListItem(
        content = {
            Text(
                text = stringResource(titleResId)
            )
        },
        lead = {
            Icon(
                imageVector = ImageVector.vectorResource(iconResId),
                contentDescription = stringResource(titleResId)
            )
        },
        modifier = modifier
    )

    GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
}

@Composable
internal fun CancelItem(
    modifier: Modifier = Modifier,
) {
    BaseListItem(
        lead = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.cancel_icon),
                contentDescription = stringResource(R.string.cancel),
                tint = Color.White
            )
        },
        content = {
            Text(
                text = stringResource(R.string.cancel),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        modifier = modifier
    )

    GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
}