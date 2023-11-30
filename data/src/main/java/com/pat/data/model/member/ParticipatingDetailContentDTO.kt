package com.pat.data.model.member

import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.pat.PatDetailContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipatingDetailContentDTO(
    @field:Json(name = "patId") override val patId: Long,
    @field:Json(name = "repImg") override val repImg: String,
    @field:Json(name = "category") override val category: String,
    @field:Json(name = "patName") override val patName: String,
    @field:Json(name = "location") override val location: String,
    @field:Json(name = "modifiedStartDate") override val startDate: String,
    @field:Json(name = "modifiedEndDate") override val endDate: String,
    @field:Json(name = "startTime") override val startTime: String,
    @field:Json(name = "endTime") override val endTime: String,
    @field:Json(name = "days") override val days: String,
    @field:Json(name = "dayList") override val dayList: List<String>,
    @field:Json(name = "patDetail") override val patDetail: String,
    @field:Json(name = "proofDetail") override val proofDetail: String,
    @field:Json(name = "bodyImg") override val bodyImg: List<String>,
    @field:Json(name = "correctImg") override val correctImg: String,
    @field:Json(name = "incorrectImg") override val incorrectImg: String,
    @field:Json(name = "realtime") override val realtime: Boolean,
    @field:Json(name = "maxProof") override val maxProof: Int,
    @field:Json(name = "myProof") override val myProof: Int,
    @field:Json(name = "allProof") override val allProof: Int,
    @field:Json(name = "allMaxProof") override val allMaxProof: Int,
    @field:Json(name = "myFailProof") override val myFailProof: Int,
    @field:Json(name = "allFailProof") override val allFailProof: Int,
    @field:Json(name = "state") override val state: String,
    @field:Json(name = "isCompleted") override val isCompleted: Boolean,
) : ParticipatingDetailContent
