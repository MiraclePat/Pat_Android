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

val convertTimeFormat: (String) -> (String) = { inputTimeString ->
    val tempList = inputTimeString.split(" ")
    val am = tempList.first()
    val hour = tempList.last().replace("시", "")
    val result = if (am == "오전") hour else (hour.toInt() + 12).toString()
    result.padStart(2, '0')+":00:00"
}