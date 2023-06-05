package com.sdamashchuk.forecast.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.common.ui.mapper.toDailyWeatherDataUIOList
import com.sdamashchuk.common.ui.model.DailyWeatherDataUIO
import com.sdamashchuk.common.ui.model.LocationUIO
import com.sdamashchuk.domain.usecase.GetDailyForecastUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class ForecastViewModel(
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ForecastViewModel.State, ForecastViewModel.SideEffect>(State()) {

    private val location: LocationUIO = checkNotNull(savedStateHandle["location"])

    init {
        viewModelScope.launch {
            getDailyForecastUseCase.invoke(GetDailyForecastUseCase.Param(location.latitude, location.longitude)).onSuccess {
                intent {
                    reduce {
                        val dailyWeatherData = it.daily
                            .toDailyWeatherDataUIOList()
                            .toPersistentList()
                        state.copy(
                            isLoading = false,
                            latitude = it.latitude,
                            longitude = it.longitude,
                            dailyWeatherData = dailyWeatherData,
                            selectedWeatherData = dailyWeatherData.first()
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

    fun sendAction(action: Action) {
        when (action) {
            is Action.DailyForecastItemSelected -> {
                intent {
                    reduce {
                        state.copy(
                            selectedWeatherData = state.dailyWeatherData[action.id]
                        )
                    }
                }
            }
        }
    }

    sealed class Action {
        data class DailyForecastItemSelected(val id: Int) : Action()
    }

    sealed class SideEffect {
        data class ShowError(val message: String?) : SideEffect()
    }

    data class State(
        val isLoading: Boolean = true,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val dailyWeatherData: List<DailyWeatherDataUIO> = persistentListOf(),
        val selectedWeatherData: DailyWeatherDataUIO? = null
    )
}