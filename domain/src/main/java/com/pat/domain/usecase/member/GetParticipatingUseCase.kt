package com.pat.domain.usecase.member

import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.repository.MemberRepository
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class GetParticipatingUseCase @Inject constructor(
    private val participatingRepository: MemberRepository,
) {
    suspend operator fun invoke(
        participatingRequestInfo: ParticipatingRequestInfo,
    ): Result<List<ParticipatingContent>> =
        participatingRepository.getParticipatingPats(participatingRequestInfo)
}
