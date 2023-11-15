package com.pat.domain.repository

import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo
import com.pat.domain.model.pat.PatDetailContent

interface PatRepository {
    suspend fun getPatDetail(): Result<PatDetailContent>

}