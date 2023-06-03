package com.sdamashchuk.overview.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.sdamashchuk.overview.viewmodel.OverviewViewModel
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.Locale
import java.util.concurrent.TimeUnit

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
            context = context,
            onLocationDetected = { latitude, longitude, countryName ->
                overviewViewModel.sendAction(
                    OverviewViewModel.Action.LocationDetected(
                        latitude = latitude,
                        longitude = longitude,
                        countryName = countryName
                    )
                )
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

@Composable
private fun OverviewScreen(
    state: State<OverviewViewModel.State>,
    context: Context,
    onLocationDetected: (Double, Double, String) -> Unit,
    showMoreClickedAction: () -> Unit
) {
    detectLocation(context, onLocationDetected)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .layout { measurable, constraints ->
                val overridenWidth = constraints.maxWidth + 2 * 24.dp.roundToPx()
                val placeable = measurable.measure(constraints.copy(maxWidth = overridenWidth))
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
    ) {
        items(state.value.hourlyWeatherData) { weatherData ->
            if (state.value.hourlyWeatherData.firstOrNull() == weatherData) {
                Spacer(modifier = Modifier.width(24.dp))
            }
            HourlyForecastChip(
                time = "${weatherData.dateTime.hour}:00",
                iconRes = weatherData.iconRes,
                temperature = weatherData.temperature,
                isSelected = false,
                onSelected = { }
            )
            if (state.value.hourlyWeatherData.lastOrNull() == weatherData) {
                Spacer(modifier = Modifier.width(24.dp))
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@SuppressLint("MissingPermission", "VisibleForTests")
private fun detectLocation(
    context: Context,
    onLocationDetected: (Double, Double, String) -> Unit
) {
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    var locationCallback: LocationCallback? = null

    locationCallback = object : LocationCallback() {

        override fun onLocationResult(result: LocationResult) {
            locationClient.lastLocation
                .addOnSuccessListener { location ->
                    getPlaceName(context, location.latitude, location.longitude) {
                        onLocationDetected.invoke(location.latitude, location.longitude, it.countryName)
                    }
                }
                .addOnFailureListener {
                    Log.e("Location error", "${it.message}")
                }
        }
    }

    locationCallback.let {
        val locationRequest: LocationRequest =
            LocationRequest.create().apply {
                interval = TimeUnit.SECONDS.toMillis(90)
                fastestInterval = TimeUnit.SECONDS.toMillis(60)
            }
        locationClient.requestLocationUpdates(
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



