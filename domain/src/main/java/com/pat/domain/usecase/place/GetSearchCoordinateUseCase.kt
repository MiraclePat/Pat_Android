package com.pat.domain.usecase.place

import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.place.CoordinateInfo
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.repository.CoordinateRepository
import com.pat.domain.repository.PatRepository
import com.pat.domain.repository.PlaceRepository
import javax.inject.Inject

class GetSearchCoordinateUseCase @Inject constructor(
    private val coordinateRepository: CoordinateRepository,
) {
    suspend operator fun invoke(
        query: String,
    ): Result<CoordinateInfo> =
        coordinateRepository.getSearchCoordinate(query)
}
