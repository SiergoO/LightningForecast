package com.sdamashchuk.overview.ui

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

@Composable
fun CurrentWeatherInfoPanel(
    modifier: Modifier = Modifier,
    humidityMeasurement: Int,
    windSpeedMeasurement: Double,
    precipitationProbabilityMeasurement: Int,
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CurrentWeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "Humidity",
            measurement = "${humidityMeasurement}%",
            iconRes = com.sdamashchuk.common.R.raw.humidity
        )
        VerticalDivider(
            modifier = Modifier
                .height(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
        )
        CurrentWeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "Wind",
            measurement = "$windSpeedMeasurement km/h",
            iconRes = com.sdamashchuk.common.R.raw.wind
        )
        VerticalDivider(
            modifier = Modifier
                .height(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

        )
        CurrentWeatherInfoItem(
            modifier = Modifier.weight(1f),
            title = "Rain",
            measurement = "${precipitationProbabilityMeasurement}%",
            iconRes = com.sdamashchuk.common.R.raw.rain
        )
    }
}