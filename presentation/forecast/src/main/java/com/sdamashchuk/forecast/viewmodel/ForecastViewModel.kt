package com.sdamashchuk.forecast.viewmodel

import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class ForecastViewModel(
    private val getForecastUseCase: GetForecastUseCase
) : BaseViewModel<ForecastViewModel.State, ForecastViewModel.SideEffect>(State()) {

    init {
        viewModelScope.launch {
            getForecastUseCase.invoke(Unit).onSuccess {
                intent {
                    reduce {
                        state.copy(
                            isLoading = false
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
        data class ShowError(val message: String?) : SideEffect()
    }

    data class State(
        val isLoading: Boolean = true
    )
}