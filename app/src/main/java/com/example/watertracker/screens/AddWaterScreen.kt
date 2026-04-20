package com.example.watertracker.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.watertracker.R
import com.example.watertracker.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.example.watertracker.API.WaterViewModel

@Composable
fun AddWaterScreen(
    navController: NavController,
    viewModel : WaterViewModel
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    var waterAmount by rememberSaveable { mutableStateOf(0) }
    var inputText by rememberSaveable { mutableStateOf("") }
    var lastUpdate by remember { mutableStateOf(0L) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    //premenne na senzor, upravuje nam natocenie podla telefonu, ci je po vyske, alebo sirke
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val values = event?.values ?: return

                val x = values[0]

                val currentTime = System.currentTimeMillis()

                if (!isLandscape) {
                    if (currentTime - lastUpdate > 50) {
                        lastUpdate = currentTime

                        if (x > 5) {
                            if (waterAmount >= 10) {
                                waterAmount -= 10
                                inputText = waterAmount.toString()
                            }
                        } else if (x < -5) {
                            if (waterAmount < 5000) {
                                waterAmount += 10
                                inputText = waterAmount.toString()
                            }
                        }
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it

                val number = it.toIntOrNull()
                if (number != null) {
                    waterAmount = number
                }
            },
            label = { Text(stringResource(R.string.add_water_input_placeholder)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "$waterAmount" + stringResource(R.string.add_water_ml_text),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))
        if (!isLandscape) {
            Row {
                Button(
                    onClick = {
                        if (waterAmount >= 10) {
                            waterAmount -= 10
                            inputText = waterAmount.toString()
                        }
                    },
                    modifier = Modifier.width(150.dp)
                        .height(48.dp)
                ) {
                    Text(stringResource(R.string.add_water_ml_minus_10))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (waterAmount < 5000) {
                            waterAmount += 10
                            inputText = waterAmount.toString()
                        }
                    },
                    modifier = Modifier.width(150.dp)
                        .height(48.dp)
                ) {
                    Text(stringResource(R.string.add_water_ml_plus_10))
                }
            }
        } else {
            Row {
                Button(
                    onClick = {
                        if (waterAmount >= 10) {
                            waterAmount -= 10
                            inputText = waterAmount.toString()
                        }
                    },
                    modifier = Modifier.width(250.dp)
                        .height(48.dp)
                ) {
                    Text(stringResource(R.string.add_water_ml_minus_10))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (waterAmount < 5000) {
                            waterAmount += 10
                            inputText = waterAmount.toString()
                        }
                    },
                    modifier = Modifier.width(250.dp)
                        .height(48.dp)
                ) {
                    Text(stringResource(R.string.add_water_ml_plus_10))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        val toastFirst = stringResource(R.string.toast_first)
        val toastSecond = stringResource(R.string.toast_second)
        val scope = rememberCoroutineScope()
            Button(
                onClick = {
                    scope.launch {

                        viewModel.addWater(waterAmount)
                        keyboardController?.hide()
                        delay(100)

                        //vypisanie toast
                        Toast.makeText(
                            context,
                            toastFirst + waterAmount + toastSecond,
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route)
                            launchSingleTop = true
                        }

                        inputText = ""
                        waterAmount = 0
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(stringResource(R.string.add_water_save))
            }
    }

    DisposableEffect(Unit) {

        sensorManager.registerListener(
            sensorListener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}