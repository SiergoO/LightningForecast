package com.sdamashchuk.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Weather(
   val current: WeatherData,
   val hourly: Map<LocalDateTime, WeatherData>,
   val daily: Map<LocalDate, WeatherData>,
   val latitude: Double,
   val longitude: Double,
   val timezone: String,
)