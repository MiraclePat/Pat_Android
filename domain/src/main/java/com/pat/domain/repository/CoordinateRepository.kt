package com.pat.domain.repository

import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.model.place.CoordinateInfo
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo

interface CoordinateRepository {

    suspend fun getSearchCoordinate(
        query : String,
    ): Result<CoordinateInfo>

}