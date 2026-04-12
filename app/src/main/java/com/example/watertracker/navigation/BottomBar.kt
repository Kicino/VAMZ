package com.example.watertracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.watertracker.R

@Composable
fun BottomBar(navController: NavController) {

    //premena na zistenie na ktorej obrazovke sme
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.bar_home)) },
            label = { Text(stringResource(R.string.bar_home)) }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Add.route,
            onClick = {
                navController.navigate(Screen.Add.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Add, contentDescription = stringResource(R.string.bar_add)) },
            label = { Text(stringResource(R.string.bar_add)) }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.History.route,
            onClick = {
                navController.navigate(Screen.History.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.AutoMirrored.Default.List, contentDescription = stringResource(R.string.bar_history)) },
            label = { Text(stringResource(R.string.bar_history)) }
        )
    }
}