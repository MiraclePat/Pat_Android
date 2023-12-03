package com.pat.domain.usecase.pat

import com.pat.domain.model.pat.HomeBannerContent
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class GetHomeBannerUseCase @Inject constructor(
    private val patRepository: PatRepository,
) {
    suspend operator fun invoke(
    ): Result<HomeBannerContent> =
        patRepository.getHomeBanner()
}
