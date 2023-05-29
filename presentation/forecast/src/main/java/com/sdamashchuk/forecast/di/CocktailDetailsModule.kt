package com.sdamashchuk.forecast.di

import com.sdamashchuk.forecast.viewmodel.ForecastViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val forecastModule = module {
    viewModelOf(::ForecastViewModel)
}