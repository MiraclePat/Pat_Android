package com.pat.domain.usecase.pat

import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class GetMapPatsUseCase @Inject constructor(
    private val patRepository: PatRepository,
) {
    suspend operator fun invoke(
        mapPatRequestInfo: MapPatRequestInfo,
    ): Result<List<MapPatContent>> =
        patRepository.getMapPats(mapPatRequestInfo)
}
