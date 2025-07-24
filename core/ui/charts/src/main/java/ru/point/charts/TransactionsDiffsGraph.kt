package ru.point.charts

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.point.utils.extensions.monthDayDateFormatter
import ru.point.vo.TransactionDiff
import java.math.RoundingMode
import kotlin.math.ceil

@OptIn(ExperimentalTextApi::class)
@Composable
fun TransactionsDiffsGraph(
    transactionDiffs: List<TransactionDiff>,
    modifier: Modifier = Modifier,
    maxAxisLabels: Int = 4,
) {

    if (transactionDiffs.isEmpty()) return

    val maxAbsoluteDiff = transactionDiffs.maxOf { it.diff.abs() }

    val density = LocalDensity.current
    val barWidth = with(density) { 6.dp.toPx() }
    val barCornerRadius = CornerRadius(with(density) { 16.dp.toPx() })
    val horizontalStartOffset = barWidth / 2f
    val xAxisLabelOffset = with(density) { 2.dp.toPx() }

    val positiveColor = Color(0xFF2AE881)
    val negativeColor = Color(0xFFFF5722)

    val labelTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 12.sp
    )

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {

        val graphHeight = size.height * 0.90f
        val totalChartWidth = size.width - horizontalStartOffset
        val totalGaps = transactionDiffs.size - 1
        val gapWidth =
            if (totalGaps > 0) ((totalChartWidth - barWidth * transactionDiffs.size) / totalGaps).coerceAtLeast(0f) else 0f

        transactionDiffs.forEachIndexed { i, e ->
            val normalizedHeight = e.diff.abs().divide(maxAbsoluteDiff, 4, RoundingMode.HALF_UP).toFloat()
            val barHeight = graphHeight * normalizedHeight
            val barCenterX = horizontalStartOffset + i * (barWidth + gapWidth) + barWidth / 2f

            drawRoundRect(
                color = if (e.isPositive) positiveColor else negativeColor,
                topLeft = Offset(barCenterX - barWidth / 2, graphHeight - barHeight),
                size = Size(barWidth, barHeight),
                cornerRadius = barCornerRadius,
            )
        }

        val totalTransactionDiffs = transactionDiffs.lastIndex

        val labelStep = ceil(totalTransactionDiffs.toFloat() / (maxAxisLabels - 1)).toInt()

        val labelIndexes = (0..totalTransactionDiffs step labelStep).toMutableSet()

        labelIndexes += totalTransactionDiffs

        labelIndexes.sorted().forEach { i ->
            val dateLabel = transactionDiffs[i].date.format(monthDayDateFormatter)
            val labelLayoutResult = textMeasurer.measure(dateLabel, style = labelTextStyle)
            val labelWidth = labelLayoutResult.size.width
            val labelCenterX = horizontalStartOffset + i * (barWidth + gapWidth) + barWidth / 2f
            val labelX = (labelCenterX - labelWidth / 2)
                .coerceIn(horizontalStartOffset, size.width - labelWidth)

            drawText(labelLayoutResult, topLeft = Offset(labelX, graphHeight + xAxisLabelOffset))
        }
    }
}