package com.pat.domain.repository

import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.HomeBannerContent
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.model.pat.PatDetailContent

interface PatRepository {
    suspend fun getHomeBanner(): Result<HomeBannerContent>
    suspend fun getHomePats(
        homePatRequestInfo: HomePatRequestInfo,
    ): Result<List<HomePatContent>>

    suspend fun getMapPats(
        mapPatRequestInfo: MapPatRequestInfo
    ): Result<List<MapPatContent>>


    suspend fun createPat(
        createPatInfo: CreatePatInfo
    ): Result<Unit>

    suspend fun getPatDetail(patId: Long): Result<PatDetailContent>
    suspend fun participatePat(patId: Long): Result<Unit>

    suspend fun updatePat(
        patId: Long,
        createPatInfo: CreatePatInfo
    ): Result<Unit>
    suspend fun deletePat(patId: Long): Result<Unit>

    suspend fun withdrawPat(patId: Long): Result<Unit>
}