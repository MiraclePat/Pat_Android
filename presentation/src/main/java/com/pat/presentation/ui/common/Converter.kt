package com.pat.presentation.ui.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


val convertMillisToDate: (Long) -> (String) = {millis ->
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
    val outputFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
    val date = if (inputDateString.isNotEmpty()) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        inputFormat.parse(inputDateString)!!
    } else {
        Date()
    }
    outputFormat.format(date)
}