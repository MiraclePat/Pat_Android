package com.pat.domain.model.member


interface ParticipatingContent {
    val patId: Long
    val repImg: String
    val patName: String
    val startDate: String
    val category: String
    val nowPerson: Int
    val maxPerson: Int
    val location: String
    val days: String
}
