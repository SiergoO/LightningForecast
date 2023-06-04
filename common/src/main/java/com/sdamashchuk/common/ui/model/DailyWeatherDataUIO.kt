package com.sdamashchuk.common.ui.model

import android.os.Parcelable
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class DailyWeatherDataUIO(
    val date: LocalDate,
    val temperatureMin: Double,
    val temperatureMax: Double,
    val sunrise: String,
    val sunset: String,
    val windSpeedMax: Double,
    val precipitationProbabilityMean: Int,
    val description: String,
    @RawRes val iconRes: Int
) : Parcelable
