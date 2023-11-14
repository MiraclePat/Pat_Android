package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.home.HomePatContentDTO
import com.pat.data.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val service: HomeService,
) {

    suspend fun getHomePats(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?,
        category: String?,
    ): ListResponse<HomePatContentDTO> {
        return service.getHomePats(page, size, sort, search, category)
    }

}
