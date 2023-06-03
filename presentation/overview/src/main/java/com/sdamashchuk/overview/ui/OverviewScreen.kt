package com.sdamashchuk.overview.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.sdamashchuk.common.ui.compose.component.AnimatedIcon
import com.sdamashchuk.overview.viewmodel.OverviewViewModel
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun OverviewScreen(
    onAreaDetected: (String) -> Unit,
    navigateToForecast: () -> Unit
) {
    val overviewViewModel = getViewModel<OverviewViewModel>()
    val state = overviewViewModel.collectAsState()
    val context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

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

    val locationDetected: (Double, Double, String) -> Unit = { latitude, longitude, area ->
        onAreaDetected.invoke(area)
        overviewViewModel.sendAction(
            OverviewViewModel.Action.LocationDetected(
                latitude = latitude,
                longitude = longitude
            )
        )
    }

    LaunchedEffect(key1 = context) {
        detectLocation(context, locationClient, locationDetected)
    }



    if (!state.value.isLoading) {
        OverviewScreen(
            state = state,
            refreshAction = {
                detectLocation(context, locationClient, locationDetected)
            },
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OverviewScreen(
    state: State<OverviewViewModel.State>,
    refreshAction: () -> Unit,
    showMoreClickedAction: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.value.isLoading,
        onRefresh = refreshAction
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                bottom = 24.dp,
                end = 24.dp
            )
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullRefreshIndicator(state.value.isLoading, pullRefreshState)
        state.value.currentWeatherData?.let { current ->
            AnimatedIcon(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                iconRes = current.iconRes
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 24.dp),
                text = "${current.temperature}°",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(72f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = current.description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            CurrentWeatherInfoPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                humidityMeasurement = current.humidity,
                windSpeedMeasurement = current.windSpeed,
                precipitationProbabilityMeasurement = current.precipitationProbability
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        HourlyForecastChipPanel(hourly = state.value.hourlyWeatherData)
    }
}

@SuppressLint("MissingPermission", "VisibleForTests")
private fun detectLocation(
    context: Context,
    client: FusedLocationProviderClient,
    onLocationDetected: (Double, Double, String) -> Unit
) {
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            client.lastLocation
                .addOnSuccessListener { location ->
                    getPlaceName(context, location.latitude, location.longitude) {
                        onLocationDetected.invoke(
                            location.latitude,
                            location.longitude,
                            "${it.locality}, ${it.countryCode}"
                        )
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
        }
    }

    locationCallback.let {
        val locationRequest: LocationRequest =
            LocationRequest.create().apply {
                interval = TimeUnit.MINUTES.toMillis(2)
            }
        client.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}

private fun getPlaceName(
    context: Context,
    latitude: Double,
    longitude: Double,
    onAddressReady: (Address) -> Unit
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
            if (addresses.isNotEmpty()) {
                onAddressReady.invoke(addresses.last())
            }
        }
    } else {
        @Suppress("DEPRECATION")
        geocoder.getFromLocation(latitude, longitude, 1)?.let { addresses ->
            if (addresses.isNotEmpty()) {
                onAddressReady.invoke(addresses.last())
            }
        }
    }
}



