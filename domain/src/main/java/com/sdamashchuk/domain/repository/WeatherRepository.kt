package com.sdamashchuk.domain.repository

import com.sdamashchuk.model.WeatherData
import java.time.LocalDateTime

interface WeatherRepository {

    suspend fun isConnected(): Boolean

    suspend fun getHourlyForecast(latitude: Double, longitude: Double): Map<LocalDateTime, WeatherData>
}