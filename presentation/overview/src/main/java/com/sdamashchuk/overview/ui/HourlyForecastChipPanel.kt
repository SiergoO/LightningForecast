package com.sdamashchuk.overview.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sdamashchuk.common.ui.model.HourlyWeatherDataUIO

@Composable
fun HourlyForecastChipPanel(
    modifier: Modifier = Modifier,
    hourly: List<HourlyWeatherDataUIO>,
    onShowDailyForecastClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onShowDailyForecastClicked.invoke() }
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Today",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "7 days",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = "7 days forecast",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        LazyRow(
            modifier = modifier

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
}