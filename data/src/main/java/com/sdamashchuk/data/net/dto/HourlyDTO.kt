package com.sdamashchuk.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDTO(
    @SerialName("relativehumidity_2m") val relativeHumidity2m: List<Int>,
    @SerialName("temperature_2m") val temperature2m: List<Double>,
    @SerialName("time") val time: List<String>,
    @SerialName("windspeed_10m") val windSpeed10m: List<Double>,
    @SerialName("winddirection_10m") val windDirection10m: List<Int>,
    @SerialName("precipitation_probability") val precipitationProbability: List<Int>,
    @SerialName("weathercode") val weatherCode: List<Int>
)