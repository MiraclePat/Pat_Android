package com.pat.presentation.ui.common

import android.util.Log
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
    // TODO 예외처리
    // 만약 입력이 빈 값이면 hour 은 0으로 처리 됩니다. 예외 처리 필요!
    val hour = tempList.last().replace("시", "").toIntOrNull() ?: 0
    val result = if (am == "오전") hour.toString() else (hour + 12).toString()
    result.padStart(2, '0')+":00:00"
}