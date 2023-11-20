package com.pat.data.model.pat

import com.pat.domain.model.pat.HomePatContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomePatContentDTO(
    @field:Json(name = "id") override val patId: Long,
    @field:Json(name = "repImg") override val repImg: String,
    @field:Json(name = "patName") override val patName: String,
    @field:Json(name = "startDate") override val startDate: String,
    @field:Json(name = "category") override val category: String,
    @field:Json(name = "nowPerson") override val nowPerson: Int,
    @field:Json(name = "maxPerson") override val maxPerson: Int,
    @field:Json(name = "location") override val location: String,
) : HomePatContent
