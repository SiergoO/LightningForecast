package com.sdamashchuk.model

import java.time.LocalDate

data class DailyWeatherData(
    val date: LocalDate,
    val temperatureMin: Double, // °C
    val temperatureMax: Double, // °C
    val windSpeedMax: Double, // km/h
    val precipitationProbabilityMean: Int, // %
    val type: WeatherType
)
