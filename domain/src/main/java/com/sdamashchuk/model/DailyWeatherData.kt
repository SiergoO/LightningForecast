package com.sdamashchuk.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyWeatherData(
    val date: LocalDate,
    val temperatureMin: Double, // °C
    val temperatureMax: Double, // °C
    val windSpeedMax: Double, // km/h
    val precipitationProbabilityMean: Int, // %
    val sunrise: LocalDateTime, // iso8601
    val sunset: LocalDateTime, // iso8601
    val type: WeatherType
)
