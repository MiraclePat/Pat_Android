package com.pat.presentation.ui.pat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.usecase.pat.GetHomePatsUseCase
import com.pat.domain.usecase.pat.GetPatDetailUseCase
import com.pat.domain.usecase.pat.ParticipatePatUseCase
import com.pat.presentation.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PatDetailUiState(
    val content: PatDetailContent? = null
)

@HiltViewModel
class PatDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPatDetailUseCase: GetPatDetailUseCase,
    private val participatePatUseCase: ParticipatePatUseCase,
) : ViewModel() {
    private val patId = savedStateHandle.get<Long?>(
        key = "patId"
    ) ?: -1

    private val _uiState = MutableStateFlow(PatDetailUiState())
    val uiState: StateFlow<PatDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getPatDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(PatDetailUiState(content = content))
            } else {
                //TODO 에러 처리
            }
        }
    }

    fun participatePat(){
        viewModelScope.launch {
            val result = participatePatUseCase(patId)
            if (result.isSuccess) {

            } else {
                //TODO 에러 처리
            }
        }
    }

}