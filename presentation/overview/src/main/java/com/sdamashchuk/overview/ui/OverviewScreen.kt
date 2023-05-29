package com.sdamashchuk.overview.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sdamashchuk.overview.viewmodel.OverviewViewModel
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OverviewScreen(
    navigateToForecast: () -> Unit
) {
    val overviewViewModel = getViewModel<OverviewViewModel>()
    val state = overviewViewModel.collectAsState()
    val context = LocalContext.current

    overviewViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is OverviewViewModel.SideEffect.NavigateToForecast -> {
                navigateToForecast.invoke()
            }

            is OverviewViewModel.SideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message ?: "", Toast.LENGTH_LONG).show()
            }
        }
    }

    if (!state.value.isLoading) {
        OverviewScreen(
            state = state,
            showMoreClickedAction = {
                overviewViewModel.sendAction(OverviewViewModel.Action.ShowMoreClicked)
            }
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
private fun OverviewScreen(
    state: State<OverviewViewModel.State>,
    showMoreClickedAction: () -> Unit
) {

}