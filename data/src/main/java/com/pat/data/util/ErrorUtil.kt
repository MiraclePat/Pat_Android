package com.pat.data.util

import com.pat.domain.model.exception.UnKnownException

fun Result<*>.exception() = exceptionOrNull() ?: UnKnownException()
