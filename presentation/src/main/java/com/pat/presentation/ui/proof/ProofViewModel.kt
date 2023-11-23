package com.pat.presentation.ui.proof

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.usecase.pat.GetMyProofUseCase
import com.pat.domain.usecase.pat.GetSomeoneProofUseCase
import com.pat.domain.usecase.pat.ProofPatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ProofUiState(
    val content: List<ProofContent>? = null
)

@HiltViewModel
class ProofViewModel @Inject constructor(
    private val proofPatUseCase: ProofPatUseCase,
    private val getMyProofUseCase: GetMyProofUseCase,
    private val getSomeoneProofUseCase: GetSomeoneProofUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProofUiState())
    val uiState: StateFlow<ProofUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getMyProofUseCase(1L, ProofRequestInfo())
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(ProofUiState(content = content))
            } else {
//                Logger.t("MainTest").i("${uiState}")
            }
        }
    }

    fun getSomeoneProof(proofImg: String) {
        viewModelScope.launch {
            val result = getSomeoneProofUseCase(2L, ProofRequestInfo())
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(ProofUiState(content = content))
            } else {
                //TODO 에러 처리
            }
        }
    }

    fun proof(proofImg: String) {
        viewModelScope.launch {
            val result = proofPatUseCase(1L, ProofPatInfo(proofImg))
            if (result.isSuccess) {
                //TODO Request to Sever?
            } else {
                //TODO 에러 처리
            }
        }
    }
}