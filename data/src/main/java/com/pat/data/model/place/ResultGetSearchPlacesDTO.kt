package com.pat.data.model.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultGetSearchPlacesDTO(
    @field:Json(name = "lastBuildDate") var lastBuildDate: String?,
    @field:Json(name = "total") var total: Int?,
    @field:Json(name = "start") var start: Int?,
    @field:Json(name = "display") var display: Int?,
    @field:Json(name = "items") var places: List<PlaceDetailDTO>
)