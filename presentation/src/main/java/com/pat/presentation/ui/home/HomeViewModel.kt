package com.pat.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.home.HomePatContent
import com.pat.domain.model.home.HomePatRequestInfo
import com.pat.domain.repository.HomeRepository
import com.pat.domain.usecase.home.GetHomePatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val content: List<HomePatContent>? = null
)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePatsUseCase: GetHomePatsUseCase,
) : ViewModel() {

    //    private val _patContent = MutableStateFlow<List<HomePatContent>?>(null)
//    val patContent: StateFlow<List<HomePatContent>?> get() = _patContent
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getHomePatsUseCase(HomePatRequestInfo())
            if (result.isSuccess) {
                val content = result.getOrThrow()
                Logger.t("MainTest").i("${content}")
                _uiState.emit(HomeUiState(content = content))
            } else {
                //TODO 에러 처리
            }

        }
    }
}