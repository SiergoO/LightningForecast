package com.sdamashchuk.forecast.viewmodel

import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.common.ui.mapper.toDailyWeatherDataUIOList
import com.sdamashchuk.common.ui.model.DailyWeatherDataUIO
import com.sdamashchuk.domain.usecase.GetDailyForecastUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class ForecastViewModel(
    private val getDailyForecastUseCase: GetDailyForecastUseCase
) : BaseViewModel<ForecastViewModel.State, ForecastViewModel.SideEffect>(State()) {

    init {
        viewModelScope.launch {
            getDailyForecastUseCase.invoke(GetDailyForecastUseCase.Param(23.33, 42.11)).onSuccess {
                intent {
                    reduce {
                        val dailyWeatherData = it.daily.toDailyWeatherDataUIOList()
                        state.copy(
                            isLoading = false,
                            latitude = it.latitude,
                            longitude = it.longitude,
                            dailyWeatherData = dailyWeatherData,
                            chosenWeatherData = dailyWeatherData.first()
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
                            chosenWeatherData = state.dailyWeatherData[action.id]
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
        val chosenWeatherData: DailyWeatherDataUIO? = null
    )
}