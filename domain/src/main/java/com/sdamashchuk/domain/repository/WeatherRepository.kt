package com.sdamashchuk.domain.repository

import com.sdamashchuk.model.Forecast

interface WeatherRepository {

    suspend fun isConnected(): Boolean

    suspend fun getForecast(): Forecast
}