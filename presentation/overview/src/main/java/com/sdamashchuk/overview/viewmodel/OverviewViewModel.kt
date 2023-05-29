package com.sdamashchuk.overview.viewmodel

import androidx.lifecycle.viewModelScope
import com.sdamashchuk.common.base.BaseViewModel
import com.sdamashchuk.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class OverviewViewModel(
    private val getForecastUseCase: GetForecastUseCase
) : BaseViewModel<OverviewViewModel.State, OverviewViewModel.SideEffect>(State()) {

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

    fun sendAction(action: Action) {
        when (action) {
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
        object ShowMoreClicked : Action()
    }

    data class State(
        val isLoading: Boolean = true
    )
}