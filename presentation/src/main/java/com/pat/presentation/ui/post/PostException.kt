package com.pat.presentation.ui.post

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val MIN_TITLE = 2
const val MAX_TITLE = 20

const val MIN_DETAIL = 15
const val MAX_DETAIL = 500

const val MIN_PERSON = 1
const val MAX_PERSON = 10000

const val MIN_PROOF = 5
const val MAX_PROOF = 300


fun compareDates(date1: String, date2: String): Boolean {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    try {
        val parsedDate1: Date = dateFormat.parse(date1) ?: return false
        val parsedDate2: Date = dateFormat.parse(date2) ?: return false

        return parsedDate1.compareTo(parsedDate2) <= 0
    } catch (e: Exception) {
        // 날짜 형식이 올바르지 않을 경우 예외 처리
        e.printStackTrace()
        return false
    }
}

fun compareTimes(time1: String, time2: String): Boolean {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    try {
        val parsedTime1: Date = timeFormat.parse(time1) ?: return false
        val parsedTime2: Date = timeFormat.parse(time2) ?: return false

        return parsedTime1.before(parsedTime2)
    } catch (e: Exception) {
        // 시간 형식이 올바르지 않을 경우 예외 처리
        e.printStackTrace()
        return false
    }
}