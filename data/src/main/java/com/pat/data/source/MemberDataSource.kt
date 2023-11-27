package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.member.ParticipatingContentDTO
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapPatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import com.pat.data.service.MemberService
import com.pat.data.service.PatService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class MemberDataSource @Inject constructor(
    private val service: MemberService,
) {
    suspend fun getParticipating(
        lastId: Long?,
        size: Int?,
        sort: String?,
        state: String?,
    ): ListResponse<ParticipatingContentDTO> {
        return service.getParticipatingPats(lastId, size, sort, state)
    }
}
