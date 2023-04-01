package com.xuannie.weatheroa.ui.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Bus Arrival Timings API Data Classes
 */
@Serializable
data class WeatherJson(
    @SerialName(value = "coord")
    val geoLocation: GeoLocation = GeoLocation(),

    @SerialName(value = "weather")
    val weatherDetails: WeatherDetails = WeatherDetails(),

    @SerialName(value = "base")
    val baseParameter: String = "No Base Parameter",

    @SerialName(value = "main")
    val weatherMetrics: WeatherMetrics = WeatherMetrics(),

    @SerialName(value = "visibility")
    val visibilityDist: Int = -1,

    @SerialName(value = "wind")
    val windDetails: WindDetails = WindDetails(),

    @SerialName(value = "clouds")
    val cloudsDetails: CloudsDetails = CloudsDetails(),

    @SerialName(value = "rain")
    val rainDetails: RainDetails? = RainDetails(),

    @SerialName(value = "snow")
    val snowDetails: SnowDetails?= SnowDetails(),

    @SerialName(value = "dt")
    val timeOfDataCalulation: Long = -1,

    @SerialName(value = "sys")
    val systemDetails: SystemDetails = SystemDetails(),

    @SerialName(value = "timezone")
    val timezoneShift: Long = -1,

    @SerialName(value = "id")
    val cityId: Long = -1,

    @SerialName(value = "name")
    val cityName: String = "No Name",

    @SerialName(value = "cod")
    val internalParameter: Int = -1,
)

@Serializable
data class SystemDetails(
    @SerialName(value = "type")
    val systemType: Int = -1,

    @SerialName(value = "id")
    val systemId: Int = -1,

    @SerialName(value = "message")
    val systemMessage: String? = "No Message",

    @SerialName(value = "country")
    val systemCountry: String = "No Country",

    @SerialName(value = "sunrise")
    val sunriseTime: Long = -1,

    @SerialName(value = "sunset")
    val sunsetTime: Long = -1,
)

@Serializable
data class RainDetails(
    @SerialName(value = "1h")
    val rainVolPastHour: Double = -1.0,

    @SerialName(value = "3h")
    val rainVolPastThreeHour: Double = -1.0,
)

@Serializable
data class SnowDetails(
    @SerialName(value = "1h")
    val snowVolPastHour: Double = -1.0,

    @SerialName(value = "3h")
    val snowVolPastThreeHour: Double = -1.0,
)

@Serializable
data class CloudsDetails(
    @SerialName(value = "all")
    val cloudinessPercentage: Int = -1,
)

@Serializable
data class WindDetails(
    @SerialName(value = "speed")
    val windSpeed: Double = -1.0,

    @SerialName(value = "deg")
    val windDirectionDegrees: Double = -1.0,

    @SerialName(value = "gust")
    val windGust: Double? = -1.0,
)

@Serializable
data class WeatherMetrics(
    @SerialName(value = "temp")
    val weatherTemperature: Double = -1.0,

    @SerialName(value = "feels_like")
    val perceivedTemperature: Double = -1.0,

    @SerialName(value = "temp_min")
    val currentMinTemperature: Double = -1.0,

    @SerialName(value = "temp_max")
    val currentMaxTemperature: Double = -1.0,

    @SerialName(value = "pressure")
    val atmosphericPressure: Int = -1,

    @SerialName(value = "humidity")
    val humidityPercentage: Int = -1,

    @SerialName(value = "sea_level")
    val atmosphericSeaPressure: Int? = -1,

    @SerialName(value = "grnd_level")
    val atmosphericGroundPressure: Int? = -1,
)

@Serializable
data class WeatherDetails(
    @SerialName(value = "id")
    val weatherConditionId: Int = -1,

    @SerialName(value = "main")
    val mainWeatherParameters: String = "NA",

    @SerialName(value = "description")
    val weatherDescription: String = "No Description",

    @SerialName(value = "icon")
    val weatherIconId: String = "No Icon",
)

@Serializable
data class GeoLocation(
    @SerialName(value = "lon")
    val longitudeCoordinate: Double = 0.0,

    @SerialName(value = "lat")
    val latitudeCoordinate: Double = 0.0,
)