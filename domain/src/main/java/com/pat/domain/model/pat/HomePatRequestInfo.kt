package com.pat.domain.model.pat

data class HomePatRequestInfo(
    val lastId: Long? = null,
    val size: Int? = null,
    val sort: String? = null,
    val query: String? = null,
    val category: String? = null,
    val showFull: Boolean? = false,
    val state: String? = "SCHEDULED",
)