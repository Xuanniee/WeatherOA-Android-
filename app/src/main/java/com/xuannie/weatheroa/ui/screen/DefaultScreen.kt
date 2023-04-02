package com.xuannie.weatheroa.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xuannie.weatheroa.R
import com.xuannie.weatheroa.ui.WeatherCard
import com.xuannie.weatheroa.ui.network.WeatherJson

@Composable
fun DefaultScreen(
    weatherUiState: WeatherUiState,
    weatherJSON: WeatherJson,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    when (weatherUiState) {
        is WeatherUiState.Success -> ResultScreen(weatherUiState.weatherJSONString, weatherJSON = weatherJSON)
        is WeatherUiState.Loading -> LoadingScreen(modifier)
        is WeatherUiState.Error -> ErrorScreen(modifier)
    }

    LaunchedEffect(true) {
        // Request location updates when the screen is first launched
        weatherViewModel.requestLocationUpdates()
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading_msg)
        )
    }
}

/**
 * The home screen displaying error message
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}

/**
 * The home screen displaying number of retrieved photos.
 */
@Composable
fun ResultScreen(
    weatherDetails: String, modifier: Modifier = Modifier,
    weatherJSON: WeatherJson
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        WeatherCard(weatherJSON = weatherJSON)
    }
}