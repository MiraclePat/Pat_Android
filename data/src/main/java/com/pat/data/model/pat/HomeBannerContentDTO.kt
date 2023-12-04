package com.pat.data.model.pat

import com.pat.domain.model.pat.HomeBannerContent
import com.pat.domain.model.pat.HomePatContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeBannerContentDTO(
    @field:Json(name = "id") override val patId: Long,
    @field:Json(name = "patName") override val patName: String,
    @field:Json(name = "date") override val date: String,
) : HomeBannerContent
