package com.sdamashchuk.data.repository

import com.sdamashchuk.data.mapper.toWeather
import com.sdamashchuk.data.net.NetworkChecker
import com.sdamashchuk.data.net.api.OpenMeteoApi
import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.model.WeatherData
import java.time.LocalDateTime

class WeatherRepositoryImpl(
    private val openMeteoApi: OpenMeteoApi,
    private val networkChecker: NetworkChecker
) : WeatherRepository {

    override suspend fun isConnected(): Boolean = networkChecker.isConnected

    override suspend fun getHourlyForecast(latitude: Double, longitude: Double): Map<LocalDateTime, WeatherData> {
        return openMeteoApi.getHourlyForecast(latitude, longitude).toWeather().hourly
    }
}