package com.pat.domain.model.member

data class ProfileUpdateRequestInfo(
    val image: ByteArray,
    val nickname: String
)