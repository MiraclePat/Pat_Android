package com.pat.domain.model.member

data class ParticipatingRequestInfo(
    val lastId: Long? = null,
    val size: Int? = null,
    val sort: String = "",
    val state: String? = "SCHEDULED",
)