package com.pat.domain.model.pat

data class CreatePatDetail(
    val patName: String,
    val patDetail: String,
    val maxPerson: Int,
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val category:String,
    val startTime:String,
    val endTime:String,
    val startDate:String,
    val endDate:String,
    val proofDetail:String,
    val days:String,
    val realtime:Boolean,
)