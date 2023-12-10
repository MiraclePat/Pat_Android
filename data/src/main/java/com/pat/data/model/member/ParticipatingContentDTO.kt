package com.pat.data.model.member

import com.pat.domain.model.member.ParticipatingContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipatingContentDTO(
    @field:Json(name = "id") override val patId: Long,
    @field:Json(name = "repImg") override val repImg: String,
    @field:Json(name = "patName") override val patName: String,
    @field:Json(name = "startDate") override val startDate: String,
    @field:Json(name = "category") override val category: String,
    @field:Json(name = "nowPerson") override val nowPerson: Int,
    @field:Json(name = "maxPerson") override val maxPerson: Int,
    @field:Json(name = "location") override val location: String,
    @field:Json(name = "days") override val days: String,
    @field:Json(name = "state") override val state: String,
    @field:Json(name = "isCompleted") override val isCompleted: Boolean,
) : ParticipatingContent
