package com.sdamashchuk.domain.usecase;

import com.sdamashchuk.domain.repository.WeatherRepository
import com.sdamashchuk.domain.usecase.base.UseCase
import com.sdamashchuk.model.Forecast
import kotlinx.coroutines.CoroutineDispatcher

class GetForecastUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<Unit, Forecast>(dispatcher) {

    override suspend fun execute(parameters: Unit): Result<Forecast> =
        try {
            val forecast = weatherRepository.getForecast()
            Result.success(forecast)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

