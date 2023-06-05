package com.sdamashchuk.app

import android.app.Application
import com.sdamashchuk.data.di.dataModules
import com.sdamashchuk.domain.di.domainModules
import com.sdamashchuk.overview.di.overviewModule
import com.sdamashchuk.forecast.di.forecastModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(overviewModule, forecastModule)
            modules(domainModules)
            modules(dataModules)
        }
        super.onCreate()
    }
}