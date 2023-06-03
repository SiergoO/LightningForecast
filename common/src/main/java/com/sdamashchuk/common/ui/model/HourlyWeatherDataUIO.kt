package com.sdamashchuk.common.ui.model

import android.os.Parcelable
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class HourlyWeatherDataUIO(
    val dateTime: LocalDateTime,
    val temperature: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val humidity: Int,
    val precipitationProbability: Int,
    val description: String,
    @RawRes val iconRes: Int
): Parcelable
