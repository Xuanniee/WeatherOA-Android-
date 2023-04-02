package com.xuannie.weatheroa.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xuannie.weatheroa.R

// Enum Class for App Routes
enum class WeatherOaAppScreen(@StringRes val title: Int) {
    Default(title = R.string.application_name),
}

@Composable
fun WeatherOaApp(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    // Save Current Back Stack Entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Name of Current Screen as a Variable
    val currentScreen = WeatherOaAppScreen.valueOf(
        backStackEntry?.destination?.route ?: WeatherOaAppScreen.Default.name
    )
    val scaffoldState = rememberScaffoldState()

    val weatherJsonUiState by weatherViewModel.weatherJsonUiState.collectAsState()

    // Top Navigation Bar
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        drawerContent = {
        },
        drawerElevation = 20.dp,
//        drawerShape = NavigationDrawer,
        drawerGesturesEnabled = true,
        topBar = {
        }
    ) { innerPadding ->

        // NavHost Composable for Navigating between Screens
        NavHost(
            navController = navController,
            startDestination = WeatherOaAppScreen.Default.name,
            modifier = modifier.padding(innerPadding)
        ) {
            // Routes for Every Screen in the App
            // 1. Default Screen
            composable(route = WeatherOaAppScreen.Default.name) {
                DefaultScreen(
                    weatherUiState = weatherViewModel.weatherUiState,
                    weatherJSON = weatherJsonUiState,
                    weatherViewModel = weatherViewModel
                )
            }
        }
    }
}