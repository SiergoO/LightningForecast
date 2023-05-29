package com.sdamashchuk.data.repository

import com.sdamashchuk.data.net.NetworkChecker
import com.sdamashchuk.data.net.api.OpenMeteoApi
import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.model.Forecast

class WeatherRepositoryImpl(
    private val openMeteoApi: OpenMeteoApi,
    private val networkChecker: NetworkChecker
) : WeatherRepository {

    override suspend fun isConnected(): Boolean = networkChecker.isConnected

    override suspend fun getForecast(): Forecast {
        return Forecast("")
    }
}