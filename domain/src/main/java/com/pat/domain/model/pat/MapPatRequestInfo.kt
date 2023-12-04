package com.pat.domain.model.pat

data class MapPatRequestInfo(
    val size: Int? = null,
    val query:String? = null,
    val category:String? = null,
    val state:String? = null,
    val showFull:Boolean? = true,
    val leftLongitude:Double? = 0.0,
    val rightLongitude:Double? = 0.0,
    val bottomLatitude:Double? = 0.0,
    val topLatitude:Double? = 0.0,
)