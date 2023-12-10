package com.pat.presentation.ui.proof

import java.util.Calendar

fun checkProofTime(start: String, end: String): Boolean {
    val currentTime = Calendar.getInstance()

    var startHour = start.substringAfter(" ").substringBefore(":").toInt()
    var endHour = end.substringAfter(" ").substringBefore(":").toInt()

    if (startHour == 12) startHour = 0
    if (endHour == 12) endHour = 0

    val adjustedStartHour = if (start.contains("오후")) startHour + 12 else startHour
    val adjustedEndHour = if (end.contains("오후")) endHour + 12 else endHour

    return currentTime.get(Calendar.HOUR_OF_DAY) in adjustedStartHour..adjustedEndHour || start == end
}

fun getDayOfWeek(): String {
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    return when (dayOfWeek) {
        Calendar.SUNDAY -> "일요일"
        Calendar.MONDAY -> "월요일"
        Calendar.TUESDAY -> "화요일"
        Calendar.WEDNESDAY -> "수요일"
        Calendar.THURSDAY -> "목요일"
        Calendar.FRIDAY -> "금요일"
        Calendar.SATURDAY -> "토요일"
        else -> throw IllegalArgumentException("Invalid day of week")
    }
}