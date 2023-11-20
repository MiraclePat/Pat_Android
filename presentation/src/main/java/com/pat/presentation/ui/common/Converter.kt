package com.pat.presentation.ui.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


val convertMillisToDate: (Long) -> (String) = { millis ->
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    formatter.format(Date(millis))
}

val convertDateFormat: (String) -> (String) = { inputDateString ->
    val outputFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
    val date = if (inputDateString.isNotEmpty()) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        inputFormat.parse(inputDateString)!!
    } else {
        Date()
    }
    outputFormat.format(date)
}

val convertTimeFormat: (String) -> (String) = { inputDateString ->
    val outputFormat = SimpleDateFormat("hh:mm:00", Locale.getDefault())
    val date = if (inputDateString.isNotEmpty()) {
        val inputFormat = SimpleDateFormat("a h시 m분", Locale.getDefault())
        inputFormat.parse(inputDateString.replace("오전", "AM").replace("오후", "PM"))!!
    } else {
        Date()
    }
    outputFormat.format(date)
}

fun convertTimeText(input: String): String {
    val inputFormat = SimpleDateFormat("a:hh:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("a h시 m분", Locale.getDefault())

    val date: Date = inputFormat.parse(input) ?: return "잘못된 형식"

    return outputFormat.format(date).replace("AM", "오전")
        .replace("PM", "오후")
}