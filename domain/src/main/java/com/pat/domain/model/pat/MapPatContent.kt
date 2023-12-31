package com.pat.domain.model.pat


interface MapPatContent {
    val patId: Long
    val repImg: String
    val patName: String
    val startDate: String
    val category: String
    val latitude: Double
    val longitude: Double
    val nowPerson: Int
    val maxPerson: Int
    val days: String
    val location: String
}
