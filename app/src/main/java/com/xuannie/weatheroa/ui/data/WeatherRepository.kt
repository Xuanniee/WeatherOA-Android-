package com.xuannie.weatheroa.ui.data

import com.xuannie.weatheroa.OPEN_WEATHER_API_KEY
import com.xuannie.weatheroa.ui.network.WeatherApiService
import com.xuannie.weatheroa.ui.network.WeatherJson


interface WeatherRepository {
//    suspend fun getWeatherDetails(cityName: String, appID: String): WeatherJson
    suspend fun getWeatherDetails(lat: Double?, lon: Double?, appID: String): WeatherJson
}

class DefaultWeatherRepository(
    private val weatherApiService: WeatherApiService
): WeatherRepository {
    override suspend fun getWeatherDetails(lat: Double?, lon: Double?, appID: String): WeatherJson {
        return weatherApiService.getWeatherDetails(lat = lat, lon = lon, appid = appID)
    }

}


