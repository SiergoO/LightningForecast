package com.sdamashchuk.common.ui.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toSimpleTime(): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    return this.format(formatter)
}