package com.pat.presentation.ui.pat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.usecase.pat.GetPatDetailUseCase
import com.pat.domain.usecase.pat.ParticipatePatUseCase
import com.pat.domain.usecase.pat.WithdrawPatUseCase
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
        viewModelScope.launch {
            val result = getPatDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(PatDetailUiState(content = content))
            } else {
                Logger.t("MainTest").i("detail viewmodel ${_uiState.value}")
            }
        }
    }

    fun participatePat() {
        viewModelScope.launch {
            val result = participatePatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
                _event.emit(ParticipateEvent.ParticipateSuccess("CANCELABLE"))
                Log.e("custom", "참여하기 성공")
            } else {
                _event.emit(ParticipateEvent.ParticipateFailed)
                Log.e("custom", "참여하기 실패")
            }
        }
    }

    fun withdrawPat(patId: Long) {
        viewModelScope.launch {
            val result = withdrawPatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
                Logger.t("MainTest").i("취소 성공")
            } else {
                Logger.t("MainTest").i("취소 실패")
            }
        }
    }
}