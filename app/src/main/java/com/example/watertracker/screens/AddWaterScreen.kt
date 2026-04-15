package com.example.watertracker.screens

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.watertracker.R

@Composable
fun AddWaterScreen(onAddWater: (Int) -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var waterAmount by remember {
        mutableStateOf(0)
    }
    var inputText by remember {
        mutableStateOf("")
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

        Row {
            Button(onClick = {
                if (waterAmount >= 10) {
                    waterAmount -= 10
                    inputText = waterAmount.toString()
                }
            },
                modifier = Modifier.width(150.dp)
            ) {
                Text(stringResource(R.string.add_water_ml_minus_10))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                if (waterAmount < 5000) {
                    waterAmount += 10
                    inputText = waterAmount.toString()
                }
            },
                modifier = Modifier.width(150.dp)
            ) {
                Text(stringResource(R.string.add_water_ml_plus_10))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        val savedText = stringResource(R.string.add_water_saved)
        val mlText = stringResource(R.string.add_water_ml_text)
        Button(
            onClick = {
                onAddWater(waterAmount)
                println(savedText + waterAmount + mlText)
                keyboardController?.hide()
                inputText = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.add_water_save))
        }
    }
}