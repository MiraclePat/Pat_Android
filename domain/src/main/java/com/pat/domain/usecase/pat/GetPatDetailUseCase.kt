package com.pat.domain.usecase.pat

import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.repository.HomeRepository
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class GetPatDetailUseCase @Inject constructor(
    private val patRepository: PatRepository,
) {
    suspend operator fun invoke(
    ): Result<PatDetailContent> =
        patRepository.getPatDetail()
}
