package com.sdamashchuk.domain.usecase;

import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.domain.usecase.base.UseCase
import com.sdamashchuk.model.WeatherData
import kotlinx.coroutines.CoroutineDispatcher
import java.time.LocalDateTime

class GetHourlyForecastUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<GetHourlyForecastUseCase.Param, Map<LocalDateTime, WeatherData>>(dispatcher) {

    data class Param(
        val latitude: Double,
        val longitude: Double
    )

    override suspend fun execute(parameters: Param): Result<Map<LocalDateTime, WeatherData>> =
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

