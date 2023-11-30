package com.pat.data.model.place

import com.squareup.moshi.Json

data class AddressDTO(
    @field:Json(name = "roadAddress") val roadAddress: String?,
    @field:Json(name = "jibunAddress") val jibunAddress: String?,
    @field:Json(name = "englishAddress") val englishAddress: String?,
    @field:Json(name = "x") val x: String?,
    @field:Json(name = "y") val y: String?,
    @field:Json(name = "distance") val distance: Double?,
    @field:Json(name = "addressElements") val addressElements: List<AddressElementDTO>?
)