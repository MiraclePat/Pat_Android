package com.pat.data.model.place

import com.squareup.moshi.Json

data class MetaDTO(
    @field:Json(name = "totalCount") val totalCount: Int?,
    @field:Json(name = "page") val page: Int?,
    @field:Json(name = "count") val count: Int?
)