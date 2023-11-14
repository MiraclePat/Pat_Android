package com.pat.domain.model.home

data class HomePatRequestInfo(
    val page: Int? = null,
    val size: Int? = null,
    val sort:String = "createdTime",
    val search:String? = null,
    val category:String? = null,
)