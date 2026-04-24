package com.example.watertracker.navigation

//zoznam obrazoviek na prepinanie
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object History : Screen("history")
    object Settings : Screen("settings")
}