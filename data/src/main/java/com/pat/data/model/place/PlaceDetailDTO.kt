package com.pat.data.model.place

import com.pat.domain.model.place.PlaceDetailInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceDetailDTO(
    @field:Json(name = "title") override var title: String?,
    @field:Json(name = "link") override var link: String?,
    @field:Json(name = "category") override var category: String?,
    @field:Json(name = "description") override var description: String?,
    @field:Json(name = "telephone") override var telephone: String?,
    @field:Json(name = "address") override var address: String?,
    @field:Json(name = "roadAddress") override var roadAddress: String?,
    @field:Json(name = "mapx") override var mapx: String?,
    @field:Json(name = "mapy") override var mapy: String?
) : PlaceDetailInfo