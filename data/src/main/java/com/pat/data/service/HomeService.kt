package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.home.HomePatContentDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("/api/test/pats/home")
    suspend fun getHomePats(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
        @Query("search") search: String?,
        @Query("category") category: String?,
    ): ListResponse<HomePatContentDTO>

}
