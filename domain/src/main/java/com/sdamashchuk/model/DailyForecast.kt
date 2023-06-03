package com.sdamashchuk.model

data class DailyForecast(
   val daily: List<DailyWeatherData>,
   val latitude: Double,
   val longitude: Double,
   val timezone: String
)