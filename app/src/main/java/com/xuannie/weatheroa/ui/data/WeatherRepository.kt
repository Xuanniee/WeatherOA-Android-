package com.xuannie.weatheroa.ui.data

import com.xuannie.weatheroa.ui.network.WeatherApiService
import com.xuannie.weatheroa.ui.network.WeatherJson


interface WeatherRepository {
    suspend fun getWeatherDetails(latitude: Double, longitude: Double): WeatherJson
}

class DefaultWeatherRepository(
    private val weatherApiService: WeatherApiService
): WeatherRepository {
    override suspend fun getWeatherDetails(latitude: Double, longitude: Double): WeatherJson {
        return weatherApiService.getWeatherDetails(
            Latitude = latitude,
            Longitude = longitude
        )
    }

}


