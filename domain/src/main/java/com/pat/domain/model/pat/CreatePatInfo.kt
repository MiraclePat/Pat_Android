package com.pat.domain.model.pat

data class CreatePatInfo(
    val repImg: String,
    val correctImg: String,
    val incorrectImg:List<String>,
    val bodyImg:List<String>,
    val pat: CreatePatInfoDetail
)