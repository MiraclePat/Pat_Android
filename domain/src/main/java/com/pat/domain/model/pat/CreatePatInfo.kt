package com.pat.domain.model.pat

data class CreatePatInfo(
    val repImg: ByteArray,
    val correctImg: ByteArray,
    val incorrectImg:ByteArray,
    val bodyImg:List<ByteArray>,
    val pat: CreatePatInfoDetail
)