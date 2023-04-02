package com.xuannie.weatheroa.ui.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.xuannie.weatheroa.OPEN_WEATHER_API_KEY
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.converter.scalars.ScalarsConverterFactory

// Defines how Retrofit talks to the web server using HTTP requests.
interface WeatherApiService {
    /**
     *  Function to get JSON Objects from URI by specifying Type of Request and Endpoint like "/photos" a URL of sorts
     */
    @GET("weather")
    suspend fun getWeatherDetails(
//        @Query("lat") lat: String = "1.2897",
//        @Query("lon") lon: String = "103.8501",
        @Query("q") cityName: String,
        @Query("appid") appid: String
    ): WeatherJson
}