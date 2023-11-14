package com.pat.domain.model.home


interface HomePatContent {
    val patId: Long
    val repImg: String
    val placeId: String
    val patName: String
    val startDate: String
    val category: String
    val nowPerson: Int
    val maxPerson: Int
}
