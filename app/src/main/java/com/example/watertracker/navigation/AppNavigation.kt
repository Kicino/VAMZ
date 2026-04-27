package com.example.watertracker.navigation

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.WaterViewModelFactory
import com.example.watertracker.dataRoom.AppDataContainer
import com.example.watertracker.screens.AddWaterScreen
import com.example.watertracker.screens.HistoryScreen
import com.example.watertracker.screens.HomeScreen
import com.example.watertracker.screens.SettingsScreen

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()//navigacia medzi screenmi
    val viewModel: WaterViewModel = viewModel(
        factory = WaterViewModelFactory(
            AppDataContainer(context).itemsRepository,
            AppDataContainer(context).userPreferencesRepository
        )
    )

    //urobenie okna v ktorom budem otvarat ostatne okna
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->

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


    ////AI generovany sposob pytania sa na autorizaciu upozorneni
    //poziadanie uzivatela o autorizovanie na upozornenia
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    ////
}