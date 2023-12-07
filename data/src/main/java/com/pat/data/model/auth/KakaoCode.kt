package com.pat.data.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoCode(
    @field:Json(name = "id") val id: String,
)
