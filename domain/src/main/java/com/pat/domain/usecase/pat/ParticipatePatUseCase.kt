package com.pat.domain.usecase.pat

import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class ParticipatePatUseCase @Inject constructor(
    private val patRepository: PatRepository,
) {
    suspend operator fun invoke(
        patId: Long,
    ): Result<Unit> =
        patRepository.participatePat(patId)
}
