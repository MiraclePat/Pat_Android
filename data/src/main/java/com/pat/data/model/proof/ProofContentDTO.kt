package com.pat.data.model.proof

import com.pat.domain.model.proof.ProofContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProofContentDTO(
    @field:Json(name = "id") override val proofId: Long,
    @field:Json(name = "proofImg") override val proofImg: String,
) : ProofContent
