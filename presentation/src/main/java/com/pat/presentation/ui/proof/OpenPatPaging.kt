package com.pat.presentation.ui.proof

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.usecase.member.GetOpenPatUseCase

class OpenPatPaging(
    private val getOpenPatUseCase: GetOpenPatUseCase,
    private val openRequestInfo: OpenPatRequestInfo
) :
    PagingSource<Int, ParticipatingContent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ParticipatingContent> {
        return try {
            val next = params.key
            val response = getOpenPatUseCase(
                openRequestInfo.copy(
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

    override fun getRefreshKey(state: PagingState<Int, ParticipatingContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}