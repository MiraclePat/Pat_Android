package com.pat.data.model.place

import com.squareup.moshi.Json

data class CoordinateResponseDTO(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "errorMessage") val errorMessage: String?,
    @field:Json(name = "meta") val metaDTO: MetaDTO?,
    @field:Json(name = "addresses") val addresses: List<AddressDTO>?
)