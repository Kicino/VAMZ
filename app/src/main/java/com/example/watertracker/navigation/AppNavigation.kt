package com.example.watertracker.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

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
                Text("Home Screen")
            }

            composable(Screen.Add.route) {
                Text("Add Screen")
            }

            composable(Screen.History.route) {
                Text("History Screen")
            }
        }
    }
}