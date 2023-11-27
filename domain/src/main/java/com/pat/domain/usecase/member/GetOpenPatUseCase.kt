package com.pat.domain.usecase.member

import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.repository.MemberRepository
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class GetOpenPatUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {
    suspend operator fun invoke(
        openPatRequestInfo: OpenPatRequestInfo,
    ): Result<List<ParticipatingContent>> =
        memberRepository.getOpenPats(openPatRequestInfo)
}
