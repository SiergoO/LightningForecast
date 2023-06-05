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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.sdamashchuk.common.ui.model.LocationUIO
import com.sdamashchuk.common.ui.util.toSimpleTime
import com.sdamashchuk.common.ui.util.toTemperatureString
import com.sdamashchuk.overview.viewmodel.OverviewViewModel
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun OverviewScreen(
    onAreaDetected: (String) -> Unit,
    navigateToDailyForecast: (location: LocationUIO) -> Unit
) {
    val overviewViewModel = getViewModel<OverviewViewModel>()
    val state = overviewViewModel.collectAsState()
    val context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationDetected: (Double, Double, String) -> Unit = { latitude, longitude, area ->
        onAreaDetected.invoke(area)
        overviewViewModel.sendAction(
            OverviewViewModel.Action.LocationDetected(
                latitude = latitude,
                longitude = longitude
            )
        )
    }
    val locationCallback = remember {
        object : LocationCallback() {
            @SuppressLint("MissingPermission")
            override fun onLocationResult(result: LocationResult) {
                locationClient.lastLocation
                    .addOnSuccessListener { location ->
                        try {
                            getPlaceName(context, location.latitude, location.longitude) {
                                locationDetected.invoke(
                                    location.latitude,
                                    location.longitude,
                                    "${it.locality}, ${it.countryCode}"
                                )
                            }
                        } catch (t: Throwable) {
                            locationClient.removeLocationUpdates(this)
                            Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        locationClient.removeLocationUpdates(this)
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
            }
        }
    }

    overviewViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is OverviewViewModel.SideEffect.NavigateToForecast -> {
                navigateToDailyForecast.invoke(sideEffect.location)
            }

            is OverviewViewModel.SideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message ?: "", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = context) {
        detectLocation(locationClient, locationCallback)
    }

    if (!state.value.isLoading) {
        OverviewScreen(
            state = state,
            refreshAction = {
                detectLocation(locationClient, locationCallback)
            },
            onShowDailyForecastClicked = {
                locationClient.removeLocationUpdates(locationCallback)
                overviewViewModel.sendAction(OverviewViewModel.Action.ShowMoreClicked)
            },
            onHourlyForecastSelected = {
                overviewViewModel.sendAction(OverviewViewModel.Action.HourlyItemSelected(it))
            },
            onHourlyForecastSelectionCanceled = {
                overviewViewModel.sendAction(OverviewViewModel.Action.HourlyItemSelectionCanceled)
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
    onShowDailyForecastClicked: () -> Unit,
    onHourlyForecastSelected: (id: Int) -> Unit,
    onHourlyForecastSelectionCanceled: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                bottom = 24.dp,
                end = 24.dp
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.value.currentWeatherData?.let { current ->
            val selected = state.value.selectedHourlyWeatherData
            Chip(
                modifier = Modifier
                    .wrapContentSize()
                    .alpha(if (selected != null) 1f else 0f),
                colors = ChipDefaults.chipColors(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                shape = ShapeDefaults.ExtraLarge,
                onClick = { onHourlyForecastSelectionCanceled.invoke() }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selected?.dateTime?.toSimpleTime() ?: "",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            AnimatedIcon(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                iconRes = selected?.iconRes ?: current.iconRes
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = (selected?.temperature ?: current.temperature).toTemperatureString(),
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
                text = selected?.description ?: current.description,
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
                humidityMeasurement = selected?.humidity ?: current.humidity,
                windSpeedMeasurement = selected?.windSpeed ?: current.windSpeed,
                precipitationProbabilityMeasurement = current.precipitationProbability
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        HourlyForecastChipPanel(
            hourly = state.value.hourlyWeatherData,
            selectedItemIndex = state.value.hourlyWeatherData.indexOf(state.value.selectedHourlyWeatherData),
            onShowDailyForecastClicked = onShowDailyForecastClicked,
            onHourlyForecastClicked = { onHourlyForecastSelected.invoke(it) }
        )
    }
}

@SuppressLint("MissingPermission", "VisibleForTests")
private fun detectLocation(
    client: FusedLocationProviderClient,
    locationCallback: LocationCallback
) {
    locationCallback.let {
        val locationRequest: LocationRequest =
            LocationRequest.create().apply {
                interval = TimeUnit.MINUTES.toMillis(1)
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