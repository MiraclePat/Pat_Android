package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.home.HomePatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PatService {

    @GET("/api/test/pats/1") //TODO 추후 수정
    suspend fun getPatDetail(
    ): PatDetailContentDTO

}
