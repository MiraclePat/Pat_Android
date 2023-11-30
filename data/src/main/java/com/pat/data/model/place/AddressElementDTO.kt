package com.pat.data.model.place

import com.squareup.moshi.Json

data class AddressElementDTO(
    @field:Json(name = "types") val types: List<String>?,
    @field:Json(name = "longName") val longName: String,
    @field:Json(name = "shortName") val shortName: String,
    @field:Json(name = "code") val code: String,
)