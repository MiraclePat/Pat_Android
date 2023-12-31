package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomeBannerContentDTO
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapListResponse
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

interface PatService {
    @GET("/api/v1/pats/home/banner")
    suspend fun getHomeBanner(): HomeBannerContentDTO
    @GET("/api/v1/pats/home")
    suspend fun getHomePats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
        @Query("query") query: String?,
        @Query("category") category: String?,
        @Query("showFull") showFull: Boolean?,
        @Query("state") state: String?,
): ListResponse<HomePatContentDTO>

    @GET("/api/v1/pats/map")
    suspend fun getMapPats(
        @Query("size") size: Int?,
        @Query("query") query: String?,
        @Query("category") category: String?,
        @Query("state") state: String?,
        @Query("showFull") showFull: Boolean?,
        @Query("leftLongitude") leftLongitude: Double?,
        @Query("rightLongitude") rightLongitude: Double?,
        @Query("bottomLatitude") bottomLatitude: Double?,
        @Query("topLatitude") topLatitude: Double?,
    ): MapListResponse

    @GET("/api/v1/pats/{pat-id}")
    suspend fun getPatDetail(
        @Path("pat-id") patId: Long,
    ): PatDetailContentDTO

    @Multipart
    @POST("/api/v1/pats")
    suspend fun createPat(
        @Part repImg : MultipartBody.Part,
        @Part correctImg : MultipartBody.Part,
        @Part incorrectImg: MultipartBody.Part,
        @Part bodyImg: List<MultipartBody.Part>,
        @Part pat: MultipartBody.Part,
    )

    @Multipart
    @PATCH("/api/v1/pats/{pat-id}")
    suspend fun updatePat(
        @Path("pat-id") patId: Long,
        @Part repImg: MultipartBody.Part,
        @Part correctImg: MultipartBody.Part,
        @Part incorrectImg: MultipartBody.Part,
        @Part bodyImg: List<MultipartBody.Part>,
        @Part pat: MultipartBody.Part,
    ): Response<Unit>

    @POST("/api/v1/pats/{pat-id}")
    suspend fun participatePat(
        @Path("pat-id") patId: Long,
    ): Response<Unit>

    @DELETE("/api/v1/pats/{pat-id}")
    suspend fun deletePat(
        @Path("pat-id") patId: Long,
    ): Response<Unit>

    @DELETE("/api/v1/pats/{pat-id}/withdraw")
    suspend fun withdrawPat(
        @Path("pat-id") patId: Long,
    ): Response<Unit>
}
