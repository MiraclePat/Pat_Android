package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.home.HomePatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import com.pat.data.service.HomeService
import com.pat.data.service.PatService
import javax.inject.Inject

class PatDataSource @Inject constructor(
    private val service: PatService,
) {

    suspend fun getPatDetail(
    ): PatDetailContentDTO {
        return service.getPatDetail()
    }

}
