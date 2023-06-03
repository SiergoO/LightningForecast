package com.sdamashchuk.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnitsDTO(
    @SerialName("relativehumidity_2m") val relativeHumidity2m: String,
    @SerialName("temperature_2m") val temperature2m: String,
    @SerialName("time") val time: String,
    @SerialName("windspeed_10m") val windSpeed10m: String,
    @SerialName("winddirection_10m") val windDirection10m: String,
    @SerialName("precipitation_probability") val precipitationProbability: String,
    @SerialName("weathercode") val weatherCode: String
)