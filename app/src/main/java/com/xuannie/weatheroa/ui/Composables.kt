package com.xuannie.weatheroa.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xuannie.weatheroa.R
import com.xuannie.weatheroa.ui.network.WeatherJson

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weatherJSON: WeatherJson
) {
    val weatherDesc: String = weatherJSON.weatherDetails[0].weatherDescription

    val weatherDescStringResource = when {
        weatherDesc.lowercase().contains("clear") -> R.string.clear_sky_str
        weatherDesc.lowercase().contains("scattered") -> R.string.scattered_clouds_str
        weatherDesc.lowercase().contains("few clouds") -> R.string.cloudy_str
        weatherDesc.lowercase().contains("broken clouds") -> R.string.broken_clouds_str
        weatherDesc.lowercase().contains("shower") -> R.string.shower_rain_str
        weatherDesc.lowercase().contains("light") -> R.string.shower_rain_str
        weatherDesc.lowercase().contains("rain") -> R.string.raining_str
        weatherDesc.lowercase().contains("thunderstorm") -> R.string.thunderstorms_str
        weatherDesc.lowercase().contains("snow") -> R.string.snowing_str
        weatherDesc.lowercase().contains("mist") -> R.string.misty_str
        else -> R.string.weather_err_str
    }

    val weatherImgStringResource = when {
        weatherDesc.lowercase().contains("clear") -> R.drawable.clear_sky
        weatherDesc.lowercase().contains("scattered") -> R.drawable.scattered_clouds
        weatherDesc.lowercase().contains("few clouds") -> R.drawable.few_clouds
        weatherDesc.lowercase().contains("broken clouds") -> R.drawable.broken_clouds
        weatherDesc.lowercase().contains("light rain") -> R.drawable.shower_rain
        weatherDesc.lowercase().contains("shower") -> R.drawable.shower_rain
        weatherDesc.lowercase().contains("rain") -> R.drawable.rain
        weatherDesc.lowercase().contains("thunderstorm") -> R.drawable.thunderstorm
        weatherDesc.lowercase().contains("snow") -> R.drawable.snow
        weatherDesc.lowercase().contains("mist") -> R.drawable.mist
        else -> R.drawable.clear_sky
    }

    Log.d("debugTag", weatherDesc.lowercase())
//    val test = weatherJSON.weatherMetrics.weatherTemperature()

    Card(modifier = modifier.padding(8.dp), elevation = 4.dp) {
        Column() {
            Image(
                painter = painterResource(weatherImgStringResource),
                contentDescription = stringResource(weatherDescStringResource),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(weatherJSON.cityName, fontWeight = FontWeight.SemiBold)
            Text(stringResource(id = weatherDescStringResource))
            Row() {
                Column(modifier.weight(5f)) {
                    Text("Current Temperature:  ${weatherJSON.weatherMetrics.weatherTemperature}")
                    Text("Minimum Temperature:  ${weatherJSON.weatherMetrics.currentMinTemperature}")
                    Text("Perceived Temperature: ${weatherJSON.weatherMetrics.perceivedTemperature}")
                    Text("Maximum Temperature: ${weatherJSON.weatherMetrics.currentMaxTemperature}")
                }

                Spacer(modifier.weight(0.2f))
                
                Column(modifier.weight(4f)) {
                    Text("Humidity (%): ${weatherJSON.weatherMetrics.humidityPercentage}")
                    Text("Pressure (hPa): ${weatherJSON.weatherMetrics.atmosphericPressure}")
                    Text("Wind Speed (m/s): ${weatherJSON.windDetails.windSpeed}")
                }
                
            }
            
            
        }
    }
}