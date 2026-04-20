package com.example.watertracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.R
import com.example.watertracker.data.HistoryItem
import com.example.watertracker.data.WeeklyChart

@Composable
fun HistoryScreen(viewModel: WaterViewModel) {

    val weeklyData by viewModel.weeklyData.collectAsState()
    val items by viewModel.allItems.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.history_screen_info),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        WeeklyChart(weeklyData)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.bar_history),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(items) { item ->
                HistoryItem(item)
            }
        }
    }
}