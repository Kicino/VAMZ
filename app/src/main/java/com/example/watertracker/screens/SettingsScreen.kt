package com.example.watertracker.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.work.WorkManager
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.R
import com.example.watertracker.reminder.scheduleReminders

@Composable
fun SettingsScreen(viewModel: WaterViewModel) {

    var notificationsEnabled by remember { mutableStateOf(true) }

    //LIST ČASOV
    val reminderTimes by viewModel.reminderTimes.collectAsState()

    // daily goal
    val dailyGoal by viewModel.dailyGoal.collectAsState()

    // dialog
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun openTimePicker(index: Int) {
        if (index !in reminderTimes.indices) return

        val current = reminderTimes[index]

        TimePickerDialog(
            context,
            { _, hour, minute ->
                val newList = reminderTimes.toMutableList()
                newList[index] = Pair(hour, minute)
                viewModel.setReminderTimes(newList)
            },
            current.first,
            current.second,
            true
        ).show()
    }

    LazyColumn( //SCROLL FIX
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(stringResource(R.string.bar_settins), style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
        }

        //SWITCH
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.scale(1.2f))
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        Text(stringResource(
                            R.string.settings_reminders),
                            modifier = Modifier.scale(1.4f)
                                .padding(12.dp)
                        )
                    }

                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = {
                            notificationsEnabled = it

                            if (it) {
                                scheduleReminders(context, reminderTimes)
                            } else {
                                WorkManager.getInstance(context).cancelAllWork()
                            }
                        }
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        //LIST ČASOV
        item {
            Text(
                stringResource(R.string.settings_reminders_times),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.scale(1.2f)
                    .padding(16.dp, 0.dp)
            )
        }

        if (reminderTimes.isNotEmpty()) {
            itemsIndexed(
                items = reminderTimes,
                key = { _, item -> item.hashCode() }
            ) { index, time ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { openTimePicker(index) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "%02d:%02d".format(time.first, time.second),
                            modifier = Modifier.scale(1.6f).padding(16.dp)
                        )

                        IconButton(
                            onClick = {
                                if (reminderTimes.size > 1) {
                                    val newList = reminderTimes.toMutableList()
                                    newList.removeAt(index)
                                    viewModel.setReminderTimes(newList)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.settings_reminders_delete_time),
                                modifier = Modifier.scale(1.2f)
                            )
                        }
                    }
                }
            }
        }

        //ADD TIME
        item {
            Button(
                onClick = {
                    viewModel.setReminderTimes(reminderTimes + Pair(12, 0))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.settings_reminders_add_time),
                    modifier = Modifier.scale(1.2f)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        //DAILY GOAL
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(stringResource(
                        R.string.settings_reminders_daily_goal) + dailyGoal + stringResource(R.string.chart_ml),
                        modifier = Modifier.scale(1.2f)
                            .padding(10.dp, 0.dp)
                    )

                    Slider(
                        value = dailyGoal.toFloat(),
                        onValueChange = { viewModel.setDailyGoal(it.toInt()) },
                        valueRange = 2000f..5000f,
                        steps = 5
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // DELETE
        item {
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.settings_reminders_delete_data))
            }
        }
    }

    //CONFIRM DIALOG
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.settings_reminders_confrim)) },
            text = { Text(stringResource(R.string.settings_reminders_question)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetData()
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.settings_reminders_yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(
                        R.string.settings_reminders_no),
                        modifier = Modifier.scale(1.2f)
                    )
                }
            }
        )
    }

    //ked sa daco zmeni,vytvori sa novy plan notifikacii
    LaunchedEffect(reminderTimes, notificationsEnabled) {
        if (notificationsEnabled) {
            if (reminderTimes.isEmpty()) {
                WorkManager.getInstance(context).cancelAllWork()
            } else {
                scheduleReminders(context, reminderTimes)
            }
        }
    }
}