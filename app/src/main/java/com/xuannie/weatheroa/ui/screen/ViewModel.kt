package com.xuannie.weatheroa.ui.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.xuannie.weatheroa.OPEN_WEATHER_API_KEY
import com.xuannie.weatheroa.ui.data.WeatherRepository
import com.xuannie.weatheroa.ui.network.WeatherJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface WeatherUiState {
    data class Success(val weatherJSONString: String) : WeatherUiState
    object Error : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    /**
     *  StateFlows to store the Data of API Calls
     */
    private val _weatherJsonUiState = MutableStateFlow(WeatherJson())
    val weatherJsonUiState: StateFlow<WeatherJson> = _weatherJsonUiState.asStateFlow()

    /** The mutable State that stores the status of the most recent request */
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    /**
     * Call getWeather() on init so we can display status immediately.
     */
    init {
        getWeather()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     */
    private fun getWeather() {
        viewModelScope.launch {
            Log.d("debugTag", "Test!!")
            weatherUiState = try {
                val listResult = weatherRepository.getWeatherDetails(cityName = "SINGAPORE", appID = OPEN_WEATHER_API_KEY)
                _weatherJsonUiState.value = WeatherJson(
                    geoLocation = listResult.geoLocation,
                    weatherDetails = listResult.weatherDetails,
                    baseParameter = listResult.baseParameter,
                    weatherMetrics = listResult.weatherMetrics,
                    visibilityDist = listResult.visibilityDist,
                    windDetails = listResult.windDetails,
                    cloudsDetails = listResult.cloudsDetails,
//                    rainDetails = listResult.rainDetails,
//                    snowDetails = listResult.snowDetails,
                    timeOfDataCalulation = listResult.timeOfDataCalulation,
                    systemDetails = listResult.systemDetails,
                    timezoneShift= listResult.timezoneShift,
                    cityId = listResult.cityId,
                    cityName = listResult.cityName,
                    internalParameter = listResult.internalParameter
                )
                Log.d("debugTag", listResult.toString())
                // Assign results from backend server to busUiState {A mutable state object that represents the status of the most recent web request}
                WeatherUiState.Success(listResult.toString())
            } catch (e: IOException) {
                WeatherUiState.Error
            } catch (e: HttpException) {
                WeatherUiState.Error
            }
        }
    }

    // Factory Object to retrieve the weatherRepo and pass it to the ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as com.xuannie.weatheroa.WeatherApplication)
                val weatherRepository = application.container.weatherRepository
                WeatherViewModel(weatherRepository = weatherRepository)
            }
        }
    }
}