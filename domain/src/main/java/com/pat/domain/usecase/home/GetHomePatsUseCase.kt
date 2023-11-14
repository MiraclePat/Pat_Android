package com.pat.domain.usecase.home

import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo
import com.pat.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomePatsUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(
        homePatRequestInfo: HomePatRequestInfo,
    ): Result<List<HomePatContent>> =
        homeRepository.getHomePats(homePatRequestInfo)
}
