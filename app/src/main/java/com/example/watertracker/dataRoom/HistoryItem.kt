package com.example.watertracker.dataRoom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

//item v skrolovaciom menu v historii
@Composable
fun HistoryItem(item: Item) {

    val date = java.text.SimpleDateFormat(
        "EEE, dd.MM.yyyy HH:mm",
        java.util.Locale.getDefault()
    ).format(java.util.Date(item.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.amount.toString() + stringResource(com.example.watertracker.R.string.chart_ml), style = MaterialTheme.typography.titleMedium)
            Text(date, style = MaterialTheme.typography.bodySmall)
        }
    }
}