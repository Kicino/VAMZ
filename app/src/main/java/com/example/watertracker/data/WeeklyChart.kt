package com.example.watertracker.data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun WeeklyChart(data: List<Pair<String, Int>>) {

    if (data.isEmpty()) return

    val values = data.map { it.second }
    val labels = data.map { it.first }

    val chartModel = entryModelOf(*values.toTypedArray())

    Column {

        Chart(
            chart = lineChart(),
            model = chartModel,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            labels.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}