package com.sdamashchuk.overview.viewmodel

import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.common.ui.model.WeatherDataUIO
import com.sdamashchuk.domain.usecase.GetHourlyForecastUseCase
import com.sdamashchuk.common.ui.mapper.toWeatherDataUIOList
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
                                        hourlyForecast = it.toWeatherDataUIOList()
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
        val hourlyForecast: List<WeatherDataUIO> = listOf()
    )
}