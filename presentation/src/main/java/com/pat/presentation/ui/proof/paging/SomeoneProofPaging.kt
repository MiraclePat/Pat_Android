package com.pat.presentation.ui.proof.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.usecase.proof.GetSomeoneProofUseCase

class SomeoneProofPaging(
    private val patId: Long,
    private val someoneProofUseCase: GetSomeoneProofUseCase,
    private val proofRequestInfo: ProofRequestInfo
) :
    PagingSource<Int, ProofContent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProofContent> {
        return try {
            val next = params.key
            val response = someoneProofUseCase(
                patId, proofRequestInfo.copy(
                    lastId = next?.toLong()
                )
            ).getOrThrow()
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = response.last().proofId.toInt()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProofContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}