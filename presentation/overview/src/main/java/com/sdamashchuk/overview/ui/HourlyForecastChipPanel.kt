package com.sdamashchuk.overview.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.sdamashchuk.common.ui.model.HourlyWeatherDataUIO

@Composable
fun HourlyForecastChipPanel(
    modifier: Modifier = Modifier,
    hourly: List<HourlyWeatherDataUIO>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .layout { measurable, constraints ->
                val overridenWidth = constraints.maxWidth + 2 * 24.dp.roundToPx()
                val placeable = measurable.measure(constraints.copy(maxWidth = overridenWidth))
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
    ) {
        items(hourly) { weatherData ->
            if (hourly.firstOrNull() == weatherData) {
                Spacer(modifier = Modifier.width(24.dp))
            }
            HourlyForecastChip(
                time = "${weatherData.dateTime.hour}:00",
                iconRes = weatherData.iconRes,
                temperature = weatherData.temperature,
                isSelected = false,
                onSelected = { }
            )
            if (hourly.lastOrNull() == weatherData) {
                Spacer(modifier = Modifier.width(24.dp))
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}