package com.sdamashchuk.model

import java.time.LocalDateTime

data class HourlyWeatherData(
    val dateTime: LocalDateTime,
    val temperature: Double, // °C
    val windSpeed: Double, // km/h
    val windDirection: Int, // °
    val humidity: Int, // %
    val precipitationProbability: Int, // %
    val type: WeatherType
)
