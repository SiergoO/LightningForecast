package com.sdamashchuk.model

data class CurrentWeatherData(
    val isDay: Boolean,
    val temperature: Double, // °C
    val windSpeed: Double, // km/h
    val windDirection: Int, // °
    val humidity: Int, // %
    val precipitationProbability: Int, // %
    val type: WeatherType
)
