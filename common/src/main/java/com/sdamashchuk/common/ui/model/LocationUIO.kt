package com.sdamashchuk.common.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationUIO(
    val latitude: Double,
    val longitude: Double
) : Parcelable