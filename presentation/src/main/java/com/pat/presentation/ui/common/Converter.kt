package com.pat.presentation.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.pat.presentation.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


val convertMillisToDate: (Long) -> (String) = { millis ->
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    formatter.format(Date(millis))
}

val convertDateFormat: (String) -> (String) = { inputDateString ->
    val outputFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
    val date = if (inputDateString.isNotEmpty() && inputDateString.length == 10) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        inputFormat.parse(inputDateString)!!
    } else {
        inputDateString
    }
    if (inputDateString.length != 10) inputDateString else outputFormat.format(date)
}

val convertTimeFormat: (String) -> (String) = { inputTimeString ->
    val tempList = inputTimeString.split(" ")
    val am = tempList.first()
    // TODO 예외처리
    // 만약 입력이 빈 값이면 hour 은 0으로 처리 됩니다. 예외 처리 필요!
    var hour = tempList.last().replace("시", "").toIntOrNull() ?: 0
    if (hour == 12) hour = 0
    val result = if (am == "오전") hour.toString() else (hour + 12).toString()
    result.padStart(2, '0') + ":00"
}

val convertDateViewFormat: (String) -> (String) = { inputTimeString ->
    val tempList = inputTimeString.split("-")
    if (tempList.size != 3) {
        inputTimeString
    } else {
        val month = tempList[1]
        val day = tempList[2]
        "${month}월 ${day}일"
    }
}

val convertTimeViewFormat: (String) -> (String) = { inputTimeString ->
    val tempList = inputTimeString.split(":")
    var hour = tempList.first().trimStart('0').toIntOrNull() ?: 0
    val am: String
    if (hour >= 12) {
        am = "오후"
        hour -= 12
    } else {
        am = "오전"
    }
    if (hour == 0) hour = 12
    am + " ${hour}시"
}

val reduceText: (String, Int) -> AnnotatedString = { text, length ->
    buildAnnotatedString {
        if (text.length > length) {
            withStyle(
                style = Typography.labelMedium.toSpanStyle()
            ) {
                append(text.substring(0, length))
            }
            withStyle(style = Typography.labelMedium.toSpanStyle()) {
                append("...")
            }
        } else {
            withStyle(style = Typography.labelMedium.toSpanStyle()) {
                append(text)
            }
        }
    }
}