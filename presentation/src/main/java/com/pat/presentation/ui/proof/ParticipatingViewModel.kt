package com.pat.presentation.ui.proof

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.usecase.member.GetParticipatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PattingUiState(
    val content: List<ParticipatingContent>? = null
)


@HiltViewModel
class PattingViewModel @Inject constructor(
    private val getParticipatingUseCase: GetParticipatingUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PattingUiState())
    val uiState: StateFlow<PattingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getParticipatingUseCase(ParticipatingRequestInfo(state = "SCHEDULED "))
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(PattingUiState(content = content))
            } else {
                Logger.t("PattingTest").i("${uiState}")
            }
        }
    }

    fun getInProgress() {
        viewModelScope.launch {
            val result = getParticipatingUseCase(ParticipatingRequestInfo(state = "IN_PROGRESS"))
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(PattingUiState(content = content))
            } else {
                Logger.t("PattingTest").i("${uiState}")
            }
        }
    }

    fun getCompleted() {
        viewModelScope.launch {
            val result = getParticipatingUseCase(ParticipatingRequestInfo(state = "COMPLETED"))
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(PattingUiState(content = content))
            } else {
                Logger.t("PattingTest").i("${uiState}")
            }
        }
    }
}