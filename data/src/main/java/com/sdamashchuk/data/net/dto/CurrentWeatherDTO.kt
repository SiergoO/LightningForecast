package com.sdamashchuk.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    @SerialName("is_day") val isDay: Int,
    @SerialName("temperature") val temperature: Double,
    @SerialName("time") val time: String,
    @SerialName("weathercode") val weatherCode: Int,
    @SerialName("winddirection") val windDirection: Double,
    @SerialName("windspeed") val windSpeed: Double
)