package com.pat.domain.model.pat

data class MapPatRequestInfo(
    val lastId: Long,
    val size: Int,
    val query:String,
    val category:String,
    val leftLongitude:Double? = 0.0,
    val rightLongitude:Double? = 0.0,
    val bottomLatitude:Double? = 0.0,
    val topLatitude:Double? = 0.0,
)