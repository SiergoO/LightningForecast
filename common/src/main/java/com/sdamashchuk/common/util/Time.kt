package com.sdamashchuk.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toSimpleTime(): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    return this.format(formatter)
}