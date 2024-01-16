package com.pat.data.model.member

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NicknameRequestBody(
    @field:Json(name = "nickname") val nickname: String,
)
