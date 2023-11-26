package com.pat.domain.model.place

data class PlaceSearchRequestInfo (
    val query: String,
    val display: Int? = null,
    val start:Int? = null,
    val sort:String? = null,
)