package com.pat.data.model.member

import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.pat.HomePatContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyProfileContentDTO(
    @field:Json(name = "profileImg") override val profileImg: String,
    @field:Json(name = "nickname") override val nickname: String,
    @field:Json(name = "finPats") override val finPats: Int,
    @field:Json(name = "openPats") override val openPats: Int,
) : MyProfileContent
