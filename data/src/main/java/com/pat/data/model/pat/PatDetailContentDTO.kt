package com.pat.data.model.pat

import com.pat.domain.model.pat.PatDetailContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatDetailContentDTO(
    @field:Json(name = "patId") override val patId: Long,
    @field:Json(name = "repImg") override val repImg: String,
    @field:Json(name = "patName") override val patName: String,
    @field:Json(name = "startDate") override val startDate: String,
    @field:Json(name = "category") override val category: String,
    @field:Json(name = "nowPerson") override val nowPerson: Int,
    @field:Json(name = "maxPerson") override val maxPerson: Int,
    @field:Json(name = "endDate") override val endDate: String,
    @field:Json(name = "startTime") override val startTime: String,
    @field:Json(name = "endTime") override val endTime: String,
    @field:Json(name = "dayList") override val dayList: List<String>,
    @field:Json(name = "proofDetail") override val proofDetail: String,
    @field:Json(name = "bodyImg") override val bodyImg: List<String>,
    @field:Json(name = "correctImg") override val correctImg: String,
    @field:Json(name = "incorrectImg") override val incorrectImg: String,
    @field:Json(name = "realtime") override val realtime: Boolean,
    @field:Json(name = "patDetail") override val patDetail: String,
    @field:Json(name = "location") override val location: String,
    @field:Json(name = "isWriter") override val isWriter: Boolean,
    @field:Json(name = "isJoiner") override val isJoiner: Boolean,
    @field:Json(name = "nickname") override val nickname: String,
    @field:Json(name = "profileImg") override val profileImg: String,
    @field:Json(name = "state") override val state: String,
) : PatDetailContent
