package com.sdamashchuk.forecast.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sdamashchuk.common.ui.compose.component.AnimatedIcon
import com.sdamashchuk.common.ui.util.toFullDateString
import com.sdamashchuk.common.ui.util.toShortenDateString
import com.sdamashchuk.common.ui.util.toShortenDayOfWeekString
import com.sdamashchuk.common.ui.util.toTemperatureString
import com.sdamashchuk.forecast.viewmodel.ForecastViewModel
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(
    onDaySelected: (date: String) -> Unit
) {
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
            state = state,
            onDailyItemSelected = {
                forecastViewModel.sendAction(ForecastViewModel.Action.DailyForecastItemSelected(it))
            },
            onDaySelected = onDaySelected
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
    onDailyItemSelected: (id: Int) -> Unit,
    onDaySelected: (date: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                bottom = 24.dp,
                end = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.value.selectedWeatherData?.let { chosen ->
            onDaySelected.invoke(chosen.date.toFullDateString())
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedIcon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    iconRes = chosen.iconRes
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chosen.temperatureMax.toTemperatureString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "/${chosen.temperatureMin.toTemperatureString()}",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = chosen.description,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            DailyWeatherInfoPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                sunrise = chosen.sunrise,
                sunset = chosen.sunset,
                windSpeedMeasurement = chosen.windSpeedMax
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.value.dailyWeatherData) { data ->
                DailyForecastItem(
                    modifier = Modifier,
                    date = data.date.toShortenDayOfWeekString(),
                    iconRes = data.iconRes,
                    description = data.description,
                    temperatureMin = data.temperatureMin.roundToInt(),
                    temperatureMax = data.temperatureMax.roundToInt(),
                    state.value.selectedWeatherData == data,
                    onItemSelected = { onDailyItemSelected.invoke(state.value.dailyWeatherData.indexOf(data)) }
                )
            }
        }
    }
}