package com.xuannie.weatheroa.ui.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.xuannie.weatheroa.OPEN_WEATHER_API_KEY
import com.xuannie.weatheroa.ui.network.WeatherApiService
import com.xuannie.weatheroa.ui.network.WeatherJson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A container is an object that contains the dependencies that the app requires.
 * These dependencies are used across the whole application, so they need to be in a common place
 * that all activities can use. You can create a subclass of the Application class and store a
 * reference to the container.
 */
interface AppContainer {
    // Abstract Properties
    val weatherRepository: WeatherRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    private val BASE_URL =
        "https://api.openweathermap.org/data/2.5/"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()
//        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))

    /**
     * A public Api object that exposes the lazy-initialized Retrofit service
     */
    private val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }


    // Override the Property in the Interface
    override val weatherRepository: WeatherRepository by lazy {
        DefaultWeatherRepository(retrofitService)
    }

}