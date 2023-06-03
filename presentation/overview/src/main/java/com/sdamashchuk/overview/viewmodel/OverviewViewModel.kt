package com.sdamashchuk.overview.viewmodel

import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.common.ui.mapper.toCurrentWeatherDataUIO
import com.sdamashchuk.common.ui.mapper.toHourlyWeatherDataUIOList
import com.sdamashchuk.common.ui.model.CurrentWeatherDataUIO
import com.sdamashchuk.common.ui.model.HourlyWeatherDataUIO
import com.sdamashchuk.domain.usecase.GetHourlyForecastUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.time.LocalDateTime

class OverviewViewModel(
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase
) : BaseViewModel<OverviewViewModel.State, OverviewViewModel.SideEffect>(State()) {

    fun sendAction(action: Action) {
        intent {
            when (action) {
                is Action.LocationDetected -> {
                    reduce {
                        state.copy(
                            latitude = action.latitude,
                            longitude = action.longitude
                        )
                    }
                    loadHourlyForecast(action.latitude, action.longitude)
                }

                is Action.ShowMoreClicked -> {
                    postSideEffect(SideEffect.NavigateToForecast)
                }
            }
        }
    }

    private fun loadHourlyForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getHourlyForecastUseCase.invoke(GetHourlyForecastUseCase.Param(latitude, longitude))
                .onSuccess {
                    intent {
                        reduce {
                            state.copy(
                                isLoading = false,
                                latitude = it.latitude,
                                longitude = it.longitude,
                                hourlyWeatherData = it.hourly
                                    .filter { it.dateTime.isAfter(LocalDateTime.now()) }
                                    .take(24)
                                    .toHourlyWeatherDataUIOList(),
                                currentWeatherData = it.current.toCurrentWeatherDataUIO()
                            )
                        }
                    }
                }.onFailure {
                    intent {
                        postSideEffect(SideEffect.ShowError(it.message))
                        reduce {
                            state.copy(
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    sealed class SideEffect {
        object NavigateToForecast : SideEffect()
        data class ShowError(val message: String?) : SideEffect()
    }

    sealed class Action {
        data class LocationDetected(val latitude: Double, val longitude: Double) : Action()
        object ShowMoreClicked : Action()
    }

    data class State(
        val isLoading: Boolean = true,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val hourlyWeatherData: List<HourlyWeatherDataUIO> = persistentListOf(),
        val currentWeatherData: CurrentWeatherDataUIO? = null
    )
}