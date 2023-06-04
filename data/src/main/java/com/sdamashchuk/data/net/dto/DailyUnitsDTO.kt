package com.sdamashchuk.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnitsDTO(
    @SerialName("time") val time: String,
    @SerialName("weathercode") val weatherCode: String,
    @SerialName("temperature_2m_min") val temperature2mMin: String,
    @SerialName("temperature_2m_max") val temperature2mMax: String,
    @SerialName("precipitation_probability_mean") val precipitationProbabilityMean: String,
    @SerialName("windspeed_10m_max") val windspeed10mMax: String,
    @SerialName("sunrise") val sunrise: String,
    @SerialName("sunset") val sunset: String
)