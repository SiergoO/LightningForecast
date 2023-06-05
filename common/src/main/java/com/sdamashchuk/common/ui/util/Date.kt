package com.sdamashchuk.common.ui.util

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.toShortenDateString(): String {
    val month = this.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    val dayOfMonth = this.dayOfMonth
    val suffix = getDayOfMonthSuffix(dayOfMonth)

    return "$month $dayOfMonth$suffix"
}

fun LocalDate.toShortenDayOfWeekString(): String {
    val dayOfWeek = this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    return if (this.dayOfMonth == LocalDate.now().dayOfMonth) "Today" else dayOfWeek
}

fun LocalDate.toFullDateString(): String {
    val month = this.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val dayOfMonth = this.dayOfMonth
    val suffix = getDayOfMonthSuffix(dayOfMonth)

    return "$month $dayOfMonth$suffix"
}

private fun getDayOfMonthSuffix(dayOfMonth: Int): String {
    if (dayOfMonth in 11..13) {
        return "th"
    }
    return when (dayOfMonth % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}