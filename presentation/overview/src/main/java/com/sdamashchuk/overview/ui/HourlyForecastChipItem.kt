package com.sdamashchuk.overview.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdamashchuk.common.ui.compose.component.AnimatedIcon
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HourlyForecastChip(
    time: String,
    @RawRes iconRes: Int,
    temperature: Double,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    val chipInteractionSource = remember { MutableInteractionSource() }
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }
    Chip(
        modifier = Modifier
            .wrapContentSize(),
        colors = if (isSelected) {
            ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        } else {
            ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            )
        },
        shape = ShapeDefaults.ExtraLarge,
        interactionSource = chipInteractionSource,
        onClick = onSelected,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = time,
                color = contentColor,
                style = MaterialTheme.typography.titleSmall
            )
            AnimatedIcon(
                modifier = Modifier.size(60.dp),
                iconRes = iconRes
            )
            Text(
                text = "${temperature.roundToInt()}°",
                color = contentColor,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}