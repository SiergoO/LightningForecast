package com.sdamashchuk.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyDTO(
    @SerialName("time") val time: List<String>,
    @SerialName("weathercode") val weatherCode: List<Int>,
    @SerialName("temperature_2m_min") val temperature2mMin: List<Double>,
    @SerialName("temperature_2m_max") val temperature2mMax: List<Double>,
    @SerialName("precipitation_probability_mean") val precipitationProbabilityMean: List<Int>,
    @SerialName("windspeed_10m_max") val windspeed10mMax: List<Double>,
    @SerialName("sunrise") val sunrise: List<String>,
    @SerialName("sunset") val sunset: List<String>
)