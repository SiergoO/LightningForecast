package com.sdamashchuk.forecast.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdamashchuk.common.ui.compose.component.VerticalDivider
import com.sdamashchuk.common.ui.compose.component.WeatherInfoItem

@Composable
fun DailyWeatherInfoPanel(
    modifier: Modifier = Modifier,
    sunrise: String,
    sunset: String,
    windSpeedMeasurement: Double,
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "Sunrise",
            measurement = sunrise,
            iconRes = com.sdamashchuk.common.R.raw.sunrise
        )
        VerticalDivider(
            modifier = Modifier
                .height(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
        )
        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "Sunset",
            measurement = sunset,
            iconRes = com.sdamashchuk.common.R.raw.sunset
        )
        VerticalDivider(
            modifier = Modifier
                .height(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
        )
        WeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "Wind",
            measurement = "$windSpeedMeasurement km/h",
            iconRes = com.sdamashchuk.common.R.raw.wind
        )
    }
}