package com.pat.domain.model.pat

data class CreatePatInfoDetail(
    val patName: String,
    val patDetail: String,
    val maxPerson: Int,
    val latitude: Int,
    val longitude: Int,
    val location: String,
    val category:String,
    val startTime:String,
    val endTime:String,
    val startDate:String,
    val endDate:String,
    val proofDetail:String,
    val days:List<String>,
    val realtime:Boolean,
)