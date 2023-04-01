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
//    @Headers(
//    )
    @GET("weather")
    suspend fun getWeatherDetails(
        @Query("lat") Latitude: Double? = null,
        @Query("lon") Longitude: Double? = null,
        @Query("appid") APIKey: String = OPEN_WEATHER_API_KEY
    ): WeatherJson
}