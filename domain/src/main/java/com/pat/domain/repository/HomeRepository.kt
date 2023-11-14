package com.pat.domain.repository

import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo

interface HomeRepository {
    suspend fun getHomePats(
        homePatRequestInfo: HomePatRequestInfo,
    ): Result<List<HomePatContent>>

}