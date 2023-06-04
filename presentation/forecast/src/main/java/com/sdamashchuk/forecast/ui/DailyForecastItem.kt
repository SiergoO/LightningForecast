package com.sdamashchuk.forecast.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sdamashchuk.common.ui.compose.component.AnimatedIcon

@Composable
fun DailyForecastItem(
    modifier: Modifier = Modifier,
    date: String,
    @RawRes iconRes: Int,
    description: String,
    temperatureMin: Int,
    temperatureMax: Int,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {
    val itemBackground =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
    Row(
        modifier = modifier
            .height(60.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(itemBackground)
            .padding(horizontal = 16.dp)
            .clickable { onItemSelected.invoke() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = date,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AnimatedIcon(
                modifier = Modifier.size(36.dp),
                iconRes = iconRes
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = description,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${if (temperatureMax >= 0) "+" else "-"}$temperatureMax°",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${if (temperatureMin >= 0) "+" else "-"}$temperatureMin°",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}