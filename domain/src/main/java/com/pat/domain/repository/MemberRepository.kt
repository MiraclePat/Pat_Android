package com.pat.domain.repository

import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.model.member.UpdateProfileInfo
import com.pat.domain.model.pat.CreatePatInfo

interface MemberRepository {
    suspend fun getMyProfile(): Result<MyProfileContent>

    suspend fun updateProfile(
        updateProfileInfo: UpdateProfileInfo
    ): Result<Unit>

    suspend fun deleteMember(): Result<Unit>

    suspend fun getParticipatingPats(
        participatingRequestInfo: ParticipatingRequestInfo,
    ): Result<List<ParticipatingContent>>

    suspend fun getOpenPats(
        openPatRequestInfo: OpenPatRequestInfo,
    ): Result<List<ParticipatingContent>> // ParticipatingContent 동일

    suspend fun getParticipatingDetailPats(
        patId: Long
    ): Result<ParticipatingDetailContent>
}