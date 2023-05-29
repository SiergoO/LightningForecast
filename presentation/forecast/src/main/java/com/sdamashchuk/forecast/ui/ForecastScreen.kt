package com.sdamashchuk.forecast.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sdamashchuk.forecast.viewmodel.ForecastViewModel
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ForecastScreen() {
    val forecastViewModel = getViewModel<ForecastViewModel>()
    val state = forecastViewModel.collectAsState()
    val context = LocalContext.current

    forecastViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ForecastViewModel.SideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message ?: "", Toast.LENGTH_LONG).show()
            }
        }
    }

    if (!state.value.isLoading) {
        ForecastScreen(
            state = state
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ForecastScreen(
    state: State<ForecastViewModel.State>,
) {
}