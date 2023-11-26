package com.pat.domain.usecase.place

import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.repository.PatRepository
import com.pat.domain.repository.PlaceRepository
import javax.inject.Inject

class GetSearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
) {
    suspend operator fun invoke(
        placeSearchRequestInfo: PlaceSearchRequestInfo,
    ): Result<List<PlaceDetailInfo>> =
        placeRepository.getSearchPlace(placeSearchRequestInfo)
}
