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
        Color(0xFF2AE881), Color(0xFFFCE300),
        Color(0xFFE46962), Color(0xFF306A42),
        Color(0xFF795548),
    ),
) {

    val categories = remember(analysisCategories) { prepareForPie(analysisCategories, context) }

    val totalPercent = remember(categories) {
        categories.sumOf { it.part.toBigDecimalPercent() }
    }

    val initialSweepAngles = remember(categories, totalPercent) {
        categories.map { category ->
            (category.part.toBigDecimalPercent() / totalPercent * BigDecimal(360)).toFloat()
        }.toMutableList().also { sweeps ->
            val sum = sweeps.sum()
            val diff = 360f - sum
            if (sweeps.isNotEmpty()) {
                sweeps[sweeps.lastIndex] = sweeps.last() + diff
            }
        }
    }

    Box(modifier) {
        Canvas(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.Center)
        ) {
            var startAngle = -90f
            val donutSize = size.minDimension
            val strokeWidth = 24f
            val radius = donutSize / 2f

            initialSweepAngles.forEachIndexed { index, sweepAngle ->
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(
                        (donutSize - radius * 2) / 2,
                        (donutSize - radius * 2) / 2
                    ),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth)
                )
                startAngle += sweepAngle
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.Start
        ) {
            categories.forEachIndexed { index, category ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(colors[index % colors.size], CircleShape)
                    )
                    Text(
                        text = "${category.emojiIcon} ${category.part} ${category.title}",
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

    val sortedByPercent = originalAnalysisCategories.sortedByDescending { it.part.toBigDecimalPercent() }

    val majorCategories = sortedByPercent.filter {
        it.part.toBigDecimalPercent() >= minPercent
    }

    val visibleMajors = majorCategories.take(maxSlices)

    val remaining = sortedByPercent - visibleMajors.toSet()

    val othersTotalPercent = remaining.sumOf { it.part.toBigDecimalPercent() }
    val othersTotalAmount = remaining.sumOf { it.amount.toBigDecimalAmount() }

    val formattedAmount = DecimalFormat("#.##").format(othersTotalAmount)
    val formattedPercent = DecimalFormat("#.##").format(othersTotalPercent) + " %"

    return buildList {
        addAll(visibleMajors)
        if (othersTotalPercent > BigDecimal.ZERO) {
            add(
                AnalysisCategory(
                    id = -1,
                    title = context.getString(R.string.other),
                    emojiIcon = "📦",
                    amount = formattedAmount,
                    currency = "",
                    part = formattedPercent
                )
            )
        }
    }
}