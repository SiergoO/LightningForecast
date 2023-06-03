package com.sdamashchuk.domain.usecase;

import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.domain.usecase.base.UseCase
import com.sdamashchuk.model.HourlyForecast
import kotlinx.coroutines.CoroutineDispatcher

class GetHourlyForecastUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<GetHourlyForecastUseCase.Param, HourlyForecast>(dispatcher) {

    data class Param(
        val latitude: Double,
        val longitude: Double
    )

    override suspend fun execute(parameters: Param): Result<HourlyForecast> =
        try {
            val hourlyForecast = weatherRepository.getHourlyForecast(
                latitude = parameters.latitude,
                longitude = parameters.longitude
            )
            Result.success(hourlyForecast)
        } catch (t: Throwable) {
            Result.failure(t)
        }
}

