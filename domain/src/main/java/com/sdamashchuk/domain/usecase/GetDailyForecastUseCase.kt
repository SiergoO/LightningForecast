package com.sdamashchuk.domain.usecase;

import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.domain.usecase.base.UseCase
import com.sdamashchuk.model.DailyForecast
import kotlinx.coroutines.CoroutineDispatcher

class GetDailyForecastUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<GetDailyForecastUseCase.Param, DailyForecast>(dispatcher) {

    data class Param(
        val latitude: Double,
        val longitude: Double
    )

    override suspend fun execute(parameters: Param): Result<DailyForecast> =
        try {
            val dailyForecast = weatherRepository.getDailyForecast(
                latitude = parameters.latitude,
                longitude = parameters.longitude
            )
            Result.success(dailyForecast)
        } catch (t: Throwable) {
            Result.failure(t)
        }
}

