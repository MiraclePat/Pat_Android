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
    val days: String //TODO LIST로 바꿔야함
    val proofDetail: String
    val bodyImg: List<String>
    val correctImg: String
    val incorrectImg: List<String>
    val realtime: Boolean
    val maxProof: Int
    val myProof: Int
    val allProof: Int
    val allMaxProof: Int
    val myFailProof: Int
    val allFailProof: Int
}
