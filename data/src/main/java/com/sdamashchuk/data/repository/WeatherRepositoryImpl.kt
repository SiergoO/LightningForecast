package com.sdamashchuk.data.repository

import com.sdamashchuk.data.mapper.toDailyForecast
import com.sdamashchuk.data.mapper.toHourlyForecast
import com.sdamashchuk.data.net.NetworkChecker
import com.sdamashchuk.data.net.api.OpenMeteoApi
import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.model.DailyForecast
import com.sdamashchuk.model.HourlyForecast

class WeatherRepositoryImpl(
    private val openMeteoApi: OpenMeteoApi,
    private val networkChecker: NetworkChecker
) : WeatherRepository {

    override suspend fun isConnected(): Boolean = networkChecker.isConnected

    override suspend fun getDailyForecast(latitude: Double, longitude: Double): DailyForecast {
        return openMeteoApi.getDailyForecast(latitude, longitude).toDailyForecast()
    }

    override suspend fun getHourlyForecast(latitude: Double, longitude: Double): HourlyForecast {
        return openMeteoApi.getHourlyForecast(latitude, longitude).toHourlyForecast()
    }
}