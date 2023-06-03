package com.sdamashchuk.overview.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
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
    val contentColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.DarkGray
    Chip(
        modifier = Modifier
            .wrapContentSize(),
        colors = if (isSelected) {
            ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        } else {
            ChipDefaults.chipColors(
                backgroundColor = Color.LightGray,
                contentColor = MaterialTheme.colorScheme.primary
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
            Box(modifier = Modifier.size(60.dp)) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(iconRes))
                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    speed = 1f
                )
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                )
            }
            Text(
                text = "${temperature.roundToInt()}°",
                color = contentColor,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}