package com.pat.presentation.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.usecase.pat.GetHomePatsUseCase

class HomePaging(
    private val getHomePatsUseCase: GetHomePatsUseCase,
    private val homePatRequestInfo: HomePatRequestInfo
) :
    PagingSource<Int, HomePatContent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomePatContent> {
        return try {
            val next = params.key
            val response = getHomePatsUseCase(
                homePatRequestInfo.copy(
                    lastId = next?.toLong()
                )
            ).getOrThrow()
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = response.last().patId.toInt()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HomePatContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private suspend fun getAdditionalDataFromServer(startIndex: Int): Result<List<HomePatContent>> {
        val additionalRequestInfo = homePatRequestInfo.copy(lastId = startIndex.toLong())
//        Logger.t("MainTest").i("info : ${additionalRequestInfo}")
        return getHomePatsUseCase(additionalRequestInfo)
    }
}