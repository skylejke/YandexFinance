package ru.point.charts


import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.point.core.resources.R
import ru.point.ui.colors.BrightYellow
import ru.point.ui.colors.CocoaBrown
import ru.point.ui.colors.CoralRed
import ru.point.ui.colors.ForestGreen
import ru.point.ui.colors.MintGreen
import ru.point.utils.extensions.toBigDecimalAmount
import ru.point.utils.extensions.toBigDecimalPercent
import ru.point.vo.AnalysisCategory
import java.math.BigDecimal
import java.text.DecimalFormat

@Composable
fun AnalysisDonutChart(
    analysisCategories: List<AnalysisCategory>,
    context: Context,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MintGreen, BrightYellow,
        CoralRed, ForestGreen,
        CocoaBrown,
    ),
) {

    val categories = remember(analysisCategories) { prepareForPie(analysisCategories, context) }

    val totalPercent = remember(categories) {
        categories.fold(BigDecimal.ZERO) { acc, category ->
            acc + category.part.toBigDecimalPercent()
        }
    }

    Box(modifier) {
        Canvas(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.Center)
        ) {
            var startAngle = -90f
            val size = size.minDimension
            val thickness = 24f
            val radius = size / 2f
            categories.forEachIndexed { index, cat ->
                val pct = cat.part.toBigDecimalPercent()
                val sweepAngle = (pct / totalPercent * BigDecimal(360)).toFloat()
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(
                        (size - radius * 2) / 2,
                        (size - radius * 2) / 2
                    ),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = thickness)
                )
                startAngle += sweepAngle
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.Start
        ) {
            categories.forEachIndexed { i, cat ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(colors[i % colors.size], CircleShape)
                    )
                    Text(
                        text = "${cat.emojiIcon} ${cat.part} ${cat.title}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

private fun prepareForPie(
    originalAnalysisCategories: List<AnalysisCategory>,
    context: Context,
    minPercent: BigDecimal = BigDecimal(5),
    maxSlices: Int = 4,
): List<AnalysisCategory> {
    if (originalAnalysisCategories.isEmpty()) return emptyList()

    val sorted = originalAnalysisCategories.sortedByDescending { it.part.toBigDecimalPercent() }

    val majors = sorted.filter { it.part.toBigDecimalPercent() >= minPercent }
    val visibleMajors = majors.take(maxSlices)

    val others = sorted - visibleMajors.toSet()

    val otherPercent = others.fold(BigDecimal.ZERO) { acc, cat ->
        acc + cat.part.toBigDecimalPercent()
    }

    val otherAmount = others.fold(BigDecimal.ZERO) { acc, cat ->
        acc + cat.amount.toBigDecimalAmount()
    }

    val df = DecimalFormat("#.##")
    val otherPartStr = df.format(otherPercent) + " %"
    val otherAmountStr = df.format(otherAmount)

    return buildList {
        addAll(visibleMajors)
        if (otherPercent > BigDecimal.ZERO) {
            add(
                AnalysisCategory(
                    id = -1,
                    title = context.getString(R.string.other),
                    emojiIcon = "📦",
                    amount = otherAmountStr,
                    currency = "",
                    part = otherPartStr
                )
            )
        }
    }
}