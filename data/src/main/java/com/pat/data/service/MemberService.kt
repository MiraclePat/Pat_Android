package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.member.ParticipatingContentDTO
import com.pat.data.model.member.ParticipatingDetailContentDTO
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapPatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberService {
    @GET("/api/v1/members/pats")
    suspend fun getParticipatingPats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("state") state: String?,
    ): ListResponse<ParticipatingContentDTO>

    @GET("/api/v1/members/pats/open")
    suspend fun getOpenPats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("state") sort: String?,
    ): ListResponse<ParticipatingContentDTO>

    @GET("/api/v1/members/pats/{pat-id}")
    suspend fun getParticipatingDetail(
        @Path("pat-id") patId: Long,
    ): ParticipatingDetailContentDTO
}