package com.xuannie.weatheroa.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
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

class WeatherViewModel(private val context: Context, private val weatherRepository: WeatherRepository) : ViewModel() {
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
        // Check permission to access location
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            weatherUiState = WeatherUiState.Error // Handle permission not granted
        } else {
            requestLocationUpdates()
        }
        getWeather()
    }

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val locationLiveData = MutableLiveData<Location>()

    private var latitude: Double? = getLocationLiveData().value?.latitude
    private var longitude: Double? = getLocationLiveData().value?.longitude

    fun getLocationLiveData(): LiveData<Location> = locationLiveData

    fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Handle permission not granted
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
    }

    private val locationListener = LocationListener { location ->
        latitude = location.latitude
        longitude = location.longitude
        locationLiveData.postValue(location)
    }
        // Implement other LocationListener methods if needed

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     */
    private fun getWeather() {
        viewModelScope.launch {
            Log.d("debugTag", "Test!!")
            weatherUiState = try {
                Log.d("debugTag", latitude.toString())
                Log.d("debugTag", longitude.toString())
                val listResult = weatherRepository.getWeatherDetails(lat = latitude, lon = longitude, appID = OPEN_WEATHER_API_KEY)
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
                WeatherViewModel(context = application.applicationContext,weatherRepository = weatherRepository)
            }
        }
    }
}