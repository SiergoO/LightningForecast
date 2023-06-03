package com.sdamashchuk.domain.repository

import com.sdamashchuk.model.DailyForecast
import com.sdamashchuk.model.HourlyForecast

interface WeatherRepository {

    suspend fun isConnected(): Boolean

    suspend fun getDailyForecast(latitude: Double, longitude: Double): DailyForecast

    suspend fun getHourlyForecast(latitude: Double, longitude: Double): HourlyForecast
}