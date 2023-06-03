package com.sdamashchuk.model

data class HourlyForecast(
    val current: CurrentWeatherData,
    val hourly: List<HourlyWeatherData>,
    val latitude: Double,
    val longitude: Double,
    val timezone: String
)
