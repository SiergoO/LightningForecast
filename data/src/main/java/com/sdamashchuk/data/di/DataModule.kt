package com.sdamashchuk.data.di

import com.sdamashchuk.data.net.HttpClientFactory
import com.sdamashchuk.data.net.NetworkChecker
import com.sdamashchuk.data.net.api.OpenMeteoApi
import com.sdamashchuk.data.repository.WeatherRepositoryImpl
import com.sdamashchuk.domain.repository.WeatherRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val netModule = module {
    single { HttpClientFactory().createHttpClient(context = get()) }
    singleOf(::NetworkChecker)
    singleOf(::OpenMeteoApi)
}

val repositoryModule = module {
    singleOf(::WeatherRepositoryImpl) { bind<WeatherRepository>() }
}

val dataModules = listOf(
    netModule,
    repositoryModule
)