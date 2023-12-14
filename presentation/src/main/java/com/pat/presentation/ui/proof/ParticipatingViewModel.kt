package com.pat.presentation.ui.proof

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.usecase.member.GetOpenPatUseCase
import com.pat.domain.usecase.member.GetParticipatingUseCase
import com.pat.presentation.ui.proof.paging.OpenPatPaging
import com.pat.presentation.ui.proof.paging.ParticipatingPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ParticipatingViewModel @Inject constructor(
    private val getParticipatingUseCase: GetParticipatingUseCase,
    private val getOpenPatUseCase: GetOpenPatUseCase,
) : ViewModel() {
    private val size = 10

    private val _query = MutableStateFlow("IN_PROGRESS")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pats = _query.flatMapLatest { state ->
        Pager(
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

    val openedPats = Pager(
        config = PagingConfig(pageSize = size),
        pagingSourceFactory = {
            OpenPatPaging(
                getOpenPatUseCase,
                OpenPatRequestInfo(
                    size = size
                )
            )
        }
    ).flow.cachedIn(viewModelScope)

    fun setState(state: String) {
        val patState = when (state) {
            "참여예정 팟" -> "SCHEDULED"
            "참여중인 팟" -> "IN_PROGRESS"
            "완료한 팟" -> "COMPLETED"
            else -> "SCHEDULED"
        }
        viewModelScope.launch {
            _query.emit(patState)
        }
    }
}