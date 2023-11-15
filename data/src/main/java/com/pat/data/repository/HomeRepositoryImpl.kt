package com.pat.data.repository

import android.util.Log
import com.pat.data.source.HomeDataSource
import com.pat.data.util.exception
import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo
import com.pat.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
    ) : HomeRepository {

    override suspend fun getHomePats(homePatRequestInfo: HomePatRequestInfo): Result<List<HomePatContent>> {
        val result = runCatching {
            homeDataSource.getHomePats(homePatRequestInfo.size,homePatRequestInfo.page,
                homePatRequestInfo.sort,homePatRequestInfo.search,homePatRequestInfo.category)
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Log.e("custom", "${result.exceptionOrNull()} ")
            Result.failure(result.exception())
        }
    }

}
