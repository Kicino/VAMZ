package com.example.watertracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.WaterViewModelFactory
import com.example.watertracker.data.AppDataContainer
import com.example.watertracker.screens.AddWaterScreen
import com.example.watertracker.screens.HistoryScreen
import com.example.watertracker.screens.HomeScreen
import com.example.watertracker.screens.SettingsScreen

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: WaterViewModel = viewModel(
        factory = WaterViewModelFactory(AppDataContainer(context).itemsRepository)
    )

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
                HomeScreen(viewModel)
            }

            composable(Screen.Add.route) {
                AddWaterScreen(navController, viewModel)
            }

            composable(Screen.History.route) {
                HistoryScreen(viewModel)
            }

            composable(Screen.Settings.route) {
                SettingsScreen(viewModel)
            }
        }
    }
}