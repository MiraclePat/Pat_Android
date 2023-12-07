package com.pat.presentation.ui.proof

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.usecase.member.GetOpenPatUseCase
import com.pat.domain.usecase.member.GetParticipatingUseCase
import com.pat.presentation.ui.home.HomePaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PattingUiState(
    val content: List<ParticipatingContent>? = null
)


@HiltViewModel
class ParticipatingViewModel @Inject constructor(
    private val getParticipatingUseCase: GetParticipatingUseCase,
    private val getOpenPatUseCase: GetOpenPatUseCase,
) : ViewModel() {
    private val size = 10

    init {
        getPatInfo()
    }

    fun getPatInfo(
        state: String? = null
    ): Flow<PagingData<ParticipatingContent>> {
        return Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                ParticipatingPaging(
                    getParticipatingUseCase,
                    ParticipatingRequestInfo(
                        state = state,
                        size = size
                    )
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getOpenPats(
        state: String? = null
    ): Flow<PagingData<ParticipatingContent>> {
        return Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                OpenPatPaging(
                    getOpenPatUseCase,
                    OpenPatRequestInfo(
                        state = state,
                        size = size
                    )
                )
            }
        ).flow.cachedIn(viewModelScope)
    }
}