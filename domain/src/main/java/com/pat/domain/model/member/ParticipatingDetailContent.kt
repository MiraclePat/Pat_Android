package com.pat.domain.model.member


interface ParticipatingDetailContent {
    val patId: Long
    val repImg: String
    val category: String
    val patName: String
    val location: String
    val startDate: String
    val endDate: String
    val startTime: String
    val endTime: String
    val days: String
    val dayList: List<String>
    val patDetail: String
    val proofDetail: String
    val bodyImg: List<String>
    val correctImg: String
    val incorrectImg: String
    val realtime: Boolean
    val maxProof: Int
    val myProof: Int
    val allProof: Int
    val allMaxProof: Int
    val myFailProof: Int
    val allFailProof: Int
    val state: String
    val isCompleted: Boolean
}
