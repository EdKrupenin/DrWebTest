package com.example.drwebtest.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drwebtest.MainActivityViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: MainActivityViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val showBackButton = currentBackStackEntry?.destination?.route != "appList"

            TopBar(
                title = "Installed Apps",
                showBackButton = showBackButton,
                navController = navController
            )
        },
        modifier = Modifier.fillMaxSize()
    )
    { innerPadding ->
        NavHost(navController = navController, startDestination = "appList") {
            composable(AppRoutes.APP_LIST) {
                AppListScreen(navController = navController, viewModel = viewModel, innerPadding = innerPadding)
            }
            composable(AppRoutes.APP_DETAIL) {
                AppDetailScreen(innerPadding = innerPadding, viewModel = viewModel)
            }
        }
    }
}

object AppRoutes {
    const val APP_LIST = "appList"
    const val APP_DETAIL = "appDetail"
}