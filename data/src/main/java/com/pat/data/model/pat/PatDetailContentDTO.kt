package com.pat.data.model.pat

import com.pat.domain.model.pat.PatDetailContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatDetailContentDTO(
    @field:Json(name = "id") override val patId: Long,
    @field:Json(name = "repImg") override val repImg: String,
    @field:Json(name = "patName") override val patName: String,
    @field:Json(name = "startDate") override val startDate: String,
    @field:Json(name = "category") override val category: String,
    @field:Json(name = "nowPerson") override val nowPerson: Int,
    @field:Json(name = "maxPerson") override val maxPerson: Int,
    @field:Json(name = "endDate") override val endDate: String,
    @field:Json(name = "startTime") override val startTime: String,
    @field:Json(name = "endTime") override val endTime: String,
    @field:Json(name = "days") override val days: String,
    @field:Json(name = "proofDetail") override val proofDetail: String,
    @field:Json(name = "bodyImg") override val bodyImg: List<String>,
    @field:Json(name = "correctImg") override val correctImg: String,
    @field:Json(name = "incorrectImg") override val incorrectImg: List<String>,
    @field:Json(name = "realtime") override val realtime: String,
    @field:Json(name = "patDetail") override val patDetail: String,
    @field:Json(name = "location") override val location: Int,
    @field:Json(name = "isWriter") override val isWriter: Boolean,
) : PatDetailContent
