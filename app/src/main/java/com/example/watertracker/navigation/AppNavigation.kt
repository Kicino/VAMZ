package com.example.watertracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watertracker.screens.AddWaterScreen
import com.example.watertracker.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var totalWater by rememberSaveable { mutableStateOf(0f) }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->

        //nastavenie vlastnosti pre kazdu obrazovku
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Home.route) {
                HomeScreen(totalWater)
            }

            composable(Screen.Add.route) {
                AddWaterScreen(navController) { addedAmount ->
                    totalWater += addedAmount
                }
            }

            composable(Screen.History.route) {
                Text("History Screen")
            }
        }
    }
}