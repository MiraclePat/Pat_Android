package com.pat.domain.model.pat


interface PatDetailContent {
    val patId: Long
    val repImg: String
    val patName: String
    val startDate: String
    val endDate: String
    val startTime: String
    val endTime: String
    val days: String //TODO LIST로 바꿔야함
    val proofDetail: String
    val bodyImg: List<String>
    val correctImg: String
    val incorrectImg: List<String>
    val realtime: Boolean
    val patDetail: String
    val category: String
    val nowPerson: Int
    val maxPerson: Int
    val location: String
    val isWriter: Boolean
}
