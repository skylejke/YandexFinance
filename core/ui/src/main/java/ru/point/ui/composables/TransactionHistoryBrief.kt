package ru.point.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.utils.extensions.toTimeHHmm
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TransactionHistoryBrief(
    endDate: String,
    amount: String,
    modifier: Modifier = Modifier,
    startDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("LLLL yyyy", Locale.forLanguageTag("ru"))),
) {

    val baseModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(horizontal = 16.dp)

    Column(modifier = modifier) {
        BaseListItem(
            content = {
                Text(
                    text = stringResource(R.string.start)
                )
            },
            trail = {
                Text(
                    text = startDate.replaceFirstChar { it.titlecase(Locale.forLanguageTag("ru")) }
                )
            },
            modifier = baseModifier
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        BaseListItem(
            content = {
                Text(
                    text = stringResource(R.string.end)
                )
            },
            trail = {
                Text(
                    text = endDate.toTimeHHmm()
                )
            },
            modifier = baseModifier
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        BaseListItem(
            content = {
                Text(
                    text = stringResource(R.string.total)
                )
            },
            trail = {
                Text(
                    text = amount
                )
            },
            modifier = baseModifier
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}