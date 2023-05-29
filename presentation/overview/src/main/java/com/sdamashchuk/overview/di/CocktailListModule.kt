package com.sdamashchuk.overview.di

import com.sdamashchuk.overview.viewmodel.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val overviewModule = module {
    viewModelOf(::OverviewViewModel)
}