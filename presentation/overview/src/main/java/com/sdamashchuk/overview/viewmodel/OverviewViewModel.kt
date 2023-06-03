package com.sdamashchuk.overview.viewmodel

import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.common.ui.mapper.toCurrentWeatherDataUIO
import com.sdamashchuk.common.ui.mapper.toHourlyWeatherDataUIOList
import com.sdamashchuk.common.ui.model.CurrentWeatherDataUIO
import com.sdamashchuk.common.ui.model.HourlyWeatherDataUIO
import com.sdamashchuk.domain.usecase.GetHourlyForecastUseCase
import com.sdamashchuk.model.CurrentWeatherData
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class OverviewViewModel(
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase
) : BaseViewModel<OverviewViewModel.State, OverviewViewModel.SideEffect>(State()) {

    fun sendAction(action: Action) {
        when (action) {
            is Action.LocationDetected -> {
                viewModelScope.launch {
                    getHourlyForecastUseCase.invoke(GetHourlyForecastUseCase.Param(action.latitude, action.longitude))
                        .onSuccess {
                            intent {
                                reduce {
                                    state.copy(
                                        isLoading = false,
                                        hourlyWeatherData = it.hourly.toHourlyWeatherDataUIOList(),
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

            is Action.ShowMoreClicked -> {
                intent {
                    postSideEffect(SideEffect.NavigateToForecast)
                }
            }
        }
    }

    sealed class SideEffect {
        object NavigateToForecast : SideEffect()
        data class ShowError(val message: String?) : SideEffect()
    }

    sealed class Action {
        data class LocationDetected(val latitude: Double, val longitude: Double, val countryName: String) : Action()
        object ShowMoreClicked : Action()
    }

    data class State(
        val isLoading: Boolean = false,
        val hourlyWeatherData: List<HourlyWeatherDataUIO> = persistentListOf(),
        val currentWeatherData: CurrentWeatherDataUIO? = null
    )
}