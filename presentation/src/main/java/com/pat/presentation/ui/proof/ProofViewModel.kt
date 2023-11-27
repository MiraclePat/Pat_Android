package com.pat.presentation.ui.proof

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.usecase.member.GetParticipatingDetailUseCase
import com.pat.domain.usecase.proof.GetMyProofUseCase
import com.pat.domain.usecase.proof.GetSomeoneProofUseCase
import com.pat.domain.usecase.proof.ProofPatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ParticipatingUiState(
    val content: ParticipatingDetailContent? = null
)

@HiltViewModel
class ProofViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val proofPatUseCase: ProofPatUseCase,
    private val getMyProofUseCase: GetMyProofUseCase,
    private val getSomeoneProofUseCase: GetSomeoneProofUseCase,
    private val getParticipatingDetailUseCase: GetParticipatingDetailUseCase,
) : ViewModel() {
    private val patId = savedStateHandle.get<Long?>(
        key = "patId"
    ) ?: -1

    private val _uiState = MutableStateFlow(ParticipatingUiState())
    val uiState: StateFlow<ParticipatingUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            val result = getParticipatingDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(ParticipatingUiState(content = content))
            } else {
                Logger.t("MainTest").i("${uiState}")
            }

//            val result = getMyProofUseCase(1L, ProofRequestInfo())
//            if (result.isSuccess) {
//                val content = result.getOrThrow()
//                _uiState.emit(ProofUiState(content = content))
//            } else {
//                Logger.t("MainTest").i("${uiState}")
//            }
        }
    }

    fun getSomeoneProof(proofImg: String) {
        viewModelScope.launch {
            val result = getSomeoneProofUseCase(2L, ProofRequestInfo())
            if (result.isSuccess) {
                val content = result.getOrThrow()
//                _uiState.emit(ProofUiState(content = content))
            } else {
                //TODO 에러 처리
            }
        }
    }

//    fun proof(proofImg: String) {
//        viewModelScope.launch {
//            val result = proofPatUseCase(1L, ProofPatInfo(proofImg))
//            if (result.isSuccess) {
//                //TODO Request to Sever?
//            } else {
//                //TODO 에러 처리
//            }
//        }
//    }
}