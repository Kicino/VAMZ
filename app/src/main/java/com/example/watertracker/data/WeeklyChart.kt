package com.example.watertracker.data

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import com.patrykandpatrick.vico.core.chart.line.LineChart
import java.util.Calendar

@Composable
fun WeeklyChart(data: List<Pair<Int, Int>>) {

    if (data.isEmpty()) return

    @Composable
    fun dayToString(day: Int): String {
        return when (day) {
            Calendar.MONDAY -> stringResource(com.example.watertracker.R.string.chart_monday)
            Calendar.TUESDAY -> stringResource(com.example.watertracker.R.string.chart_thursday)
            Calendar.WEDNESDAY -> stringResource(com.example.watertracker.R.string.chart_wendsday)
            Calendar.THURSDAY -> stringResource(com.example.watertracker.R.string.chart_thurstday)
            Calendar.FRIDAY ->stringResource(com.example.watertracker.R.string.chart_friday)
            Calendar.SATURDAY -> stringResource(com.example.watertracker.R.string.chart_saturday)
            Calendar.SUNDAY -> stringResource(com.example.watertracker.R.string.chart_sunday)
            else -> ""
        }
    }

    val labels = data.map { dayToString(it.first) }
    val values = data.map { it.second }

    val chartModel = entryModelOf(*values.toTypedArray())
    val ml_text = stringResource(com.example.watertracker.R.string.chart_ml)

    val startAxis = rememberStartAxis(
        valueFormatter = { value, _ -> value.toInt().toString() + ml_text }
    )

    val bottomAxis = rememberBottomAxis(
        valueFormatter = { value, _ ->
            labels.getOrNull(value.toInt()) ?: ""
        }
    )

    Column {
        Chart(
            chart = lineChart(
                lines = listOf(
                    LineChart.LineSpec(
                        lineColor = Color(0xFF2196F3).toArgb(),
                        lineThicknessDp = 4f
                    )
                )
            ),
            model = chartModel,
            startAxis = startAxis,
            bottomAxis = bottomAxis,

        )
    }
}