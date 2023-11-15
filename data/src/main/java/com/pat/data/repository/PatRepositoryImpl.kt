package com.pat.data.repository

import com.orhanobut.logger.Logger
import com.pat.data.source.HomeDataSource
import com.pat.data.source.PatDataSource
import com.pat.data.util.exception
import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.repository.HomeRepository
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class PatRepositoryImpl @Inject constructor(
    private val patDataSource: PatDataSource,
    ) : PatRepository {

    override suspend fun getPatDetail(): Result<PatDetailContent> {
        val result = runCatching {
            patDataSource.getPatDetail()
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")

            Result.failure(result.exception())
        }
    }

}
