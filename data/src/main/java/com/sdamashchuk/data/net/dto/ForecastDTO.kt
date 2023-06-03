package com.sdamashchuk.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    @SerialName("current_weather") val currentWeather: CurrentWeatherDTO,
    @SerialName("elevation") val elevation: Double,
    @SerialName("generationtime_ms") val generationTimeMs: Double,
    @SerialName("hourly") val hourly: HourlyDTO,
    @SerialName("hourly_units") val hourlyUnits: HourlyUnitsDTO,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds") val utcOffsetSeconds: Int
)