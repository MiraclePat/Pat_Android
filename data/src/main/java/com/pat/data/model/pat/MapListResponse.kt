package com.pat.data.model.pat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapListResponse(
    @field:Json(name = "content") val content: List<MapPatContentDTO>,
)
