package com.pat.presentation.ui.pat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.usecase.pat.DeletePatUseCase
import com.pat.domain.usecase.pat.GetHomePatsUseCase
import com.pat.domain.usecase.pat.GetPatDetailUseCase
import com.pat.domain.usecase.pat.ParticipatePatUseCase
import com.pat.domain.usecase.pat.UpdatePatUseCase
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
    private val deletePatUseCase: DeletePatUseCase,
    private val updatePatUseCase: UpdatePatUseCase,
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

    fun participatePat() {
        viewModelScope.launch {
            Log.e("custom", "patid : $patId")
            val result = participatePatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
                Log.e("custom", "성공")
            } else {
                Log.e("custom", "fail")
            }
        }
    }

    fun deletePat(patId: Long) {
        viewModelScope.launch {
            val result = deletePatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
            } else {
                //TODO 에러 처리
            }
        }
    }

    fun updatePat(patId: Long, pat: CreatePatInfoDetail) {
        viewModelScope.launch {
//            val result =
//                updatePatUseCase(patId, CreatePatInfo("", "", listOf(), listOf(), pat))
//            if (result.isSuccess) {
//                result.getOrThrow()
//            } else {
//                //TODO 에러 처리
//            }
        }
    }
}