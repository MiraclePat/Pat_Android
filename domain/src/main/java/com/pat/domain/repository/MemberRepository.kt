package com.pat.domain.repository

import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo

interface MemberRepository {

    suspend fun getParticipatingPats(
        participatingRequestInfo: ParticipatingRequestInfo,
    ): Result<List<ParticipatingContent>>
}