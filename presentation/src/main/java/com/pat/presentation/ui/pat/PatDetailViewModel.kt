package com.pat.presentation.ui.pat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.usecase.pat.GetPatDetailUseCase
import com.pat.domain.usecase.pat.ParticipatePatUseCase
import com.pat.domain.usecase.pat.WithdrawPatUseCase
import com.pat.presentation.util.resultException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ParticipateEvent {
    data class ParticipateSuccess(val patState: String) : ParticipateEvent()
    object ParticipateFailed : ParticipateEvent()
    object GetPatDetailSuccess : ParticipateEvent()
    object GetPatDetailFail : ParticipateEvent()
    object WithdrawSuccess : ParticipateEvent()
    object WithdrawFail : ParticipateEvent()
}
data class PatDetailUiState(
    val content: PatDetailContent? = null
)

@HiltViewModel
class PatDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPatDetailUseCase: GetPatDetailUseCase,
    private val participatePatUseCase: ParticipatePatUseCase,
    private val withdrawPatUseCase: WithdrawPatUseCase,
) : ViewModel() {
    private val patId = savedStateHandle.get<Long?>(
        key = "patId"
    ) ?: -1

    private val _event = MutableSharedFlow<ParticipateEvent>()
    val event = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(PatDetailUiState())
    val uiState: StateFlow<PatDetailUiState> = _uiState.asStateFlow()

    init {
        getPatDetail()
    }
    private fun getPatDetail() {
        viewModelScope.launch {
            val result = getPatDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _event.emit(ParticipateEvent.GetPatDetailSuccess)
                _uiState.emit(PatDetailUiState(content = content))
            } else {
                _event.emit(ParticipateEvent.GetPatDetailFail)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }

    fun participatePat() {
        viewModelScope.launch {
            val result = participatePatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
                _event.emit(ParticipateEvent.ParticipateSuccess("CANCELABLE"))
                getPatDetail()
            } else {
                _event.emit(ParticipateEvent.ParticipateFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }

    fun withdrawPat(patId: Long) {
        viewModelScope.launch {
            val result = withdrawPatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
                _event.emit(ParticipateEvent.WithdrawSuccess)
                getPatDetail()
            } else {
                _event.emit(ParticipateEvent.WithdrawFail)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }
}