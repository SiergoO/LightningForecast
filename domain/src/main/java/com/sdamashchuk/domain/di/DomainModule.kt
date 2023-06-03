package com.sdamashchuk.domain.di

import com.sdamashchuk.domain.usecase.GetHourlyForecastUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    single { Dispatchers.IO }
    factoryOf(::GetHourlyForecastUseCase)
}

val domainModules = listOf(
    useCaseModule
)