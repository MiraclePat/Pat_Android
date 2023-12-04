package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.member.MyProfileContentDTO
import com.pat.data.model.member.ParticipatingContentDTO
import com.pat.data.model.member.ParticipatingDetailContentDTO
import com.pat.data.service.MemberService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class MemberDataSource @Inject constructor(
    private val service: MemberService,
) {
    suspend fun getMyProfile(): MyProfileContentDTO {
        return service.getMyProfile()
    }

    suspend fun updateProfile(
        image: MultipartBody.Part,
        nickname: String
    ): Response<Unit> {
        return service.updateProfile(image, nickname)
    }

    suspend fun deleteMember(): Response<Unit> {
        return service.deleteMember()
    }

    suspend fun getParticipating(
        lastId: Long?,
        size: Int?,
        state: String?,
    ): ListResponse<ParticipatingContentDTO> {
        return service.getParticipatingPats(lastId, size, state)
    }

    suspend fun getOpenPats(
        lastId: Long?,
        size: Int?,
        sort: String?,
    ): ListResponse<ParticipatingContentDTO> {
        return service.getOpenPats(lastId, size, sort)
    }

    suspend fun getParticipatingDetail(
        patId: Long
    ): ParticipatingDetailContentDTO {
        return service.getParticipatingDetail(patId)
    }
}
