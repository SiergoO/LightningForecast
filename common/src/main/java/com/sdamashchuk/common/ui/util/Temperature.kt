package com.sdamashchuk.common.ui.util

import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun Double.toTemperatureString(): String {
    val sign = if (this >= 0) "+" else "-"
    val temperature = this.roundToInt().absoluteValue
    return "$sign$temperature°"
}

fun Int.toTemperatureString(): String {
    val sign = if (this >= 0) "+" else "-"
    val temperature = this.absoluteValue
    return "$sign$temperature°"
}