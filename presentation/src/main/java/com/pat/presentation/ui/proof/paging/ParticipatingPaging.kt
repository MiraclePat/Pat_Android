package com.pat.presentation.ui.proof.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.usecase.member.GetParticipatingUseCase

class ParticipatingPaging(
    private val getParticipatingUseCase: GetParticipatingUseCase,
    private val participatingRequestInfo: ParticipatingRequestInfo
) :
    PagingSource<Int, ParticipatingContent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ParticipatingContent> {
        return try {
            val next = params.key
            val response = getParticipatingUseCase(
                participatingRequestInfo.copy(
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